#this script populates a large dataframe for all past data and a new one for today's data
#in the future, the scores_historical data will have been stored, so we only need to ingest today's data

import os
import pandas as pd
from pandas import DataFrame, Series

def wavg(group):
    d = group['score']
    w = group['freq']
    return (d*w).sum() / w.sum()


if __name__ == '__main__':
     today = "2015/03/18"
     #would have this set to today's date, which we will take to be 3/18/2015 in this exercise

     scores_historic = pd.DataFrame()

     meta_historic = pd.DataFrame()

     skips = ['.DS_Store', 'dtCount','iviab','iviab_160x600', 'iviab_300x250','iviab_728x90','lang','niv','pac','smp','sus','top']
     meta = ['browser','countryCode','dma','host','operatingsystem','serverName','stateCode','userAgentStr','version']
     sad = ["sadEvidence"]

     for root, dirs, files in os.walk("2015"):
          for file in files:
          	if file not in skips + meta + sad :
          		frame = pd.read_csv(os.path.join(root,file),header=None)
          		frame["type"] = file
          		frame["date"]= root
          		scores_historic = scores_historic.append(frame, ignore_index=True)
          	if file in meta :
          		frame = pd.read_csv(os.path.join(root,file),header=None)
          		frame["type"] = file
          		frame["date"]= root
          		meta_historic = meta_historic.append(frame, ignore_index=True)

     scores_historic = scores_historic[scores_historic[0]!= "None"]
     scores_historic.columns = ['score','count','freq','type','date']
     scores_historic.score = scores_historic.score.astype(int)
     scores_group = scores_historic.groupby(['date','type'], as_index=False)
     scores_group = scores_group.apply(wavg)
     scores_group.name = "wavg_score"
     scores_group.reset_index().set_index(['date','type']).sortlevel(0).to_csv('scores.csv')


     meta_historic = meta_historic[meta_historic[0]!= "None"]
     meta_historic.columns =  ['name','count','freq','type','date']
     meta_historic.to_csv('meta.csv', index=False)
