#this script populates a large dataframe for all past data and a new one for today's data
#in the future, the scores_historical data will have been stored, so we only need to ingest today's data

import os
import pandas as pd
from pandas import DataFrame, Series

if __name__ == '__main__':
     today = "2015/03/18"
     #would have this set to today's date, which we will take to be 3/18/2015 in this exercise

     scores_historic = pd.DataFrame()
     scores_current = pd.DataFrame()

     # meta_historic = pd.DataFrame()
     # meta_current = pd.DataFrame()

     # sad_historic = pd.DataFrame()
     # sad_current = pd.DataFrame()

     #all of these 
     # skips = ['.DS_Store']
     # meta = ['browser','countryCode','dma','host','operatingsystem','serverName','stateCode','userAgentStr','version']
     # sad = ["sadEvidence"]

     for root, dirs, files in os.walk("2015"):
          for file in files:
          	if file != ".DS_Store":
          		if root != today:
          			frame = pd.read_csv(os.path.join(root,file),header=None)
          			frame["type"] = file
          			frame["date"]= root
          			scores_historic = scores_historic.append(frame, ignore_index=True)
          		else:
          			frame = pd.read_csv(os.path.join(root,file),header=None)
          			frame["type"] = file
          			frame["date"]= root
          			scores_current = scores_current.append(frame, ignore_index=True)
          	
     # scores_historic = scores_historic[scores_historic[0]!= "None"]
     # scores_current = scores_current[scores_current[0]!= "None"]
     # scores_historic.columns = ['score','count','freq','type','date']
     # scores_current.columns = ['score','count','freq','type','date']


     # meta_historic = meta_historic[meta_historic[0]!= "None"]
     # meta_current = meta_current[meta_current[0]!= "None"]
     # meta_historic.columns =  ['name','count','freq','type','date']
     # meta_current.columns = ['name','count','freq','type','date']

     # sad_historic = sad_historic[sad_historic[0]!= "None"]
     # sad_current = sad_current[sad_current[0]!= "None"]
     # sad_historic.columns =  ['name','flag','count','freq','type','date']
     # sad_current.columns = ['name','flag','count','freq','type','date']

     scores_historic.to_csv(path_or_buf='scores_historic', index=False)
     scores_current.to_csv(path_or_buf='scores_current', index=False)
     # meta_historic.to_csv(path_or_buf='meta_historic', index=False)
     # meta_current.to_csv(path_or_buf='meta_current', index=False)
     # sad_historic.to_csv(path_or_buf='sad_historic', index=False)
     # sad_current.to_csv(path_or_buf='sad_current', index=False)
