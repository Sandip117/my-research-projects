import re
import os
import json
import torch.nn as nn
import nltk
from nltk import pos_tag,word_tokenize
from afinn import Afinn
import csv
import numpy as np
from sklearn.neural_network import MLPClassifier
from sklearn.metrics import accuracy_score,f1_score
from sklearn.feature_extraction.text import TfidfVectorizer
import string
from nltk.sentiment.vader import SentimentIntensityAnalyzer
#nltk.download('vader_lexicon')
#from gensim.models import KeyedVectors
import warnings

warnings.filterwarnings("ignore")

#model = KeyedVectors.load_word2vec_format(os.getcwd()+'\\GoogleNews-vectors-negative300.bin.gz', binary=True)
##########################################################################################
# loading nearby words for 5 features
# ambiance-234 words
# service-707 words
# cost-641 words
# food-662 words
# hygiene-355 words
##########################################################################################
def main():
  afinn = Afinn(emoticons=True)
  sid = SentimentIntensityAnalyzer()

  #print(ss)
  #print(afinn.score("'Milkweed Cafe' ambiance is really nice :(."))
  hygieneW=[]
  f1 = open("yelp/hygiene.txt", "r",encoding='utf-8')
  for line in f1.readlines():
    line=line.replace("\n","")
    hygieneW.append(line)
  #print(hygieneW)
  f1.close()

  ambianceW=[]
  f2 = open("yelp/ambiance.txt", "r",encoding='utf-8')
  for line in f2.readlines():
    line=line.replace("\n","")
    ambianceW.append(line)
  #print(ambianceW)
  f2.close()

  foodW=[]
  f3 = open("yelp/food.txt", "r",encoding='utf-8')
  for line in f3.readlines():
    line=line.replace("\n","")
    foodW.append(line)
  #print(foodW)
  f3.close()

  serviceW=[]
  f4= open("yelp/service.txt", "r",encoding='utf-8')
  for line in f4.readlines():
    line=line.replace("\n","")
    serviceW.append(line)
  #print(serviceW)
  f4.close()

  costW=[]
  f5= open("yelp/cost.txt", "r",encoding='utf-8')
  for line in f5.readlines():
    line=line.replace("\n","")
    costW.append(line)
  #print(costW)
  f5.close()
  #######################################################################################################
  # getting text review, rating and other data
  #######################################################################################################
  businessIDList=[]
  ratingList=[]
  reviewList=[]
  path = "yelp/"
  #f11 = open(os.getcwd() + "\\yelp\\verb-50000.txt", "w", encoding='utf-8')
  with open(path+'business_review.csv',encoding='latin') as csvfile:
    readCSV = csv.reader(csvfile, delimiter=',')
    for row in readCSV:
        s=0
        ########for rows with no data############
        if (row[0] == ""):
            continue

        businessIDList.append(row[1])
        ratingList.append(row[2])
        r=row[3]
        r=r.replace("\n"," ")
        r=r.replace(" +"," ")
        reviewList.append(r)

        # r=pos_tag(word_tokenize(r))
        # #print(r)
        # for m in range(len(r)):
        #  s+=r[m].count("VB")
        #f11.write(str(s))
        #print(s)
        #f11.write("\n")
        if(len(reviewList)==1000): break
  #f11.close()
  verbList=[]
  verbTestList=[]
  verbs={}
  with open(path+'verbs-1000.csv', encoding='latin') as csvfile:
      count=1
      readCSV = csv.reader(csvfile, delimiter=',')
      for row in readCSV:
          if(count%2==1):
              for i in row:
                 verbs.setdefault(i,0)
                 verbs[i]+=1
          count+=1

  for i, w in enumerate(verbs, 0):
      verbs[w] = i
  feature = []
  testfeature=[]
  sentFeature = []
  trainP=[]
  testP=[]
  sentTrain=[]
  sentTest=[]
  exclude=[]
  ex = set(string.punctuation)
  while (len(ex)>0):
      exclude.append(ex.pop())
  len1=len(exclude)
  for i in range(len(verbs)):
      sentFeature.append(0)
  for d in reviewList:
      t=[]
      for k in range(len1):
          t.append(0)
      sent=[]
      for k in range(4):
          sent.append(0)
      for z in d:
          if z in exclude:
              for k in range(len1):
                  if(z==exclude[k]):
                      t[k]+=1
      if(len(trainP)<900):
          trainP.append(t)
      else: testP.append(t)
      ss = sid.polarity_scores(d)
      sent.append(ss['neg'])
      sent.append(ss['pos'])
      sent.append(ss['neu'])
      sent.append(ss['compound'])
      if(len(sentTrain)<900):
         sentTrain.append(sent)
      else:
          sentTest.append(sent)



      sentFeature = []
      for i in range(len(verbs)):
          sentFeature.append(0)
      a = word_tokenize(d.lower())
      for j in a:
          if(j in verbs.keys()):
           x = verbs.get(j)
           sentFeature[x] += 1
      if(len(feature)<900):
        feature.append(sentFeature)
      else: testfeature.append(sentFeature)
  trainVerb = np.array(feature)
  testVerb=np.array(testfeature)
  trainPunc=np.array(trainP)
  testPunc=np.array(testP)

  with open(path + 'verbs-1000.csv',encoding='latin') as csvfile:
    #readCSV = csv.reader(csvfile, delimiter=',')
    #count=1
    for row in csvfile.readlines():

        ########for rows with no data############


       #if(count%2==1):
        if(len(verbList)<900):
         verbList.append(row)
        else: verbTestList.append(row)
       #count+=1
        #print(r)
        #if(len(reviewList)==1000): break
  ##############################################################################################################
  # getting feature lists for ambiance, food, cost, service and hygiene and fullfeatureList
  ##############################################################################################################
  # getting all NA pairs in corpus



  #V1 = []
  #V2=[]
  #f51 = open(os.getcwd() + "\\yelp\\word2vec.txt", "w", encoding='utf-8')
  # for sentence in reviewList:
  #     c = sent_vectorizer(word_tokenize(sentence), model)
  #     if(len(V1)<950):
  #       V1.append(c)
  #     else: V2.append(c)
  # V = np.array(V1)
  # V3=np.array(V2)

  NAPair=[]
  naFullFeature=[]
  nounList=[]
  nounTestList=[]
  with open(path + 'noun-adjective-10-reviews.csv',encoding='latin') as csvfile:
    readCSV = csv.reader(csvfile, delimiter=',')
    count=1
    for row in readCSV:
        if (count % 2 == 1):
         if (len(nounList) < 900):
            nounList.append(len(row))
         else:
            nounTestList.append(len(row))
        pairs = []
        if(count%2==1):
          for i in row:
            pairs.append(i)
            if i not in naFullFeature:
              naFullFeature.append(i)
          NAPair.append(pairs)
        count+=1
    #print(NAPair)

  ################################################################################################################
  # preparing dictionary with index
  featureDict={}
  index=0
  for k in naFullFeature:
      featureDict.setdefault(k,0)
      featureDict[k]=index
      index+=1
  ###############################################################################################################
  #preparing individual feature categories NA pairs
  ambianceFullFeature=[]
  costFullFeature=[]
  foodFullFeature=[]
  hygieneFullFeature=[]
  serviceFullFeature=[]
  for k in naFullFeature:
      a = k.split(", ")
      b = a[0]
      b = b[1:]
      c = a[1]
      c = c[0:len(c) - 1]
      d = []
      d.append(b.lower())
      d.append(c.lower())
      if (d[0] in ambianceW):
          ambianceFullFeature.append(k)
      if (d[0] in foodW):
          foodFullFeature.append(k)
      if (d[0] in hygieneW):
          hygieneFullFeature.append(k)
      if (d[0] in serviceW):
          serviceFullFeature.append(k)
      if (d[0] in costW):
          costFullFeature.append(k)
  ambDict = {}
  a = 0
  for k in ambianceFullFeature:
      ambDict.setdefault(k, 0)
      ambDict[k] = a
      a += 1
  costDict = {}
  c = 0
  for k in costFullFeature:
      costDict.setdefault(k, 0)
      costDict[k] = c
      c += 1
  foodDict = {}
  f = 0
  for k in foodFullFeature:
      foodDict.setdefault(k, 0)
      foodDict[k] = f
      f += 1
  serDict = {}
  s = 0
  for k in serviceFullFeature:
      serDict.setdefault(k, 0)
      serDict[k] = s
      s += 1
  hygDict = {}
  h = 0
  for k in hygieneFullFeature:
      hygDict.setdefault(k, 0)
      hygDict[k] = h
      h += 1
  ###############################################################################################################
  ambianceScore=[]
  costScore=[]
  hygieneScore=[]
  foodScore=[]
  serviceScore=[]
  ############lists to be used for model##################################
  fullModelFeature=[]
  fullModelAmbiance=[]
  fullModelCost=[]
  fullModelFood=[]
  fullModelHygiene=[]
  fullModelService=[]
  newF=[]
  fNewList=[]
  test=[]
  print(len(naFullFeature))
  for j in NAPair:
    print(j)
    featureList = []
    ambList=[]
    sList=[]
    hygList=[]
    costList=[]
    fList=[]
    newList=[]
    for k in range(len(naFullFeature)):
       featureList.append(0)
    for a in range(len(ambianceFullFeature)):
        ambList.append(0)
    for s in range(len(serviceFullFeature)):
        sList.append(0)
    for h in range(len(hygieneFullFeature)):
        hygList.append(0)
    for c in range(len(costFullFeature)):
        costList.append(0)
    for f in range(len(foodFullFeature)):
        fList.append(0)
    ambScore=0.0
    cosScore=0.0
    hygScore=0.0
    serScore=0.0
    fooScore=0.0
    for k in j:

        a=k.split(", ")
        b=a[0]
        b=b[1:]
        c=a[1]
        c=c[0:len(c)-1]
        d=[]
        d.append(b.lower())
        d.append(c.lower())
        s = afinn.score(d[1]+" "+d[0])
        z = pos_tag(word_tokenize(d[1] + " " + d[0]))
        x=z[0]
        y=x[1]
        pol=0.0
        if (y.__contains__("JJS")):
            pol=5.0
        elif(y.__contains__("JJR")):
            pol=3.0
        else: pol=1.0
        if k in featureDict.keys():
            l=featureDict[k]
            featureList[l]=s*pol
            if(s*pol==0):
                featureList[l]=1
        if k in ambDict.keys():
            a=ambDict[k]
            ambList[a]=s*pol
            if (s * pol == 0):
                ambList[a]=1
        if k in serDict.keys():
            a=serDict[k]
            sList[a]=s*pol
            if (s * pol == 0):
                sList[a]=1
        if k in foodDict.keys():
            a=foodDict[k]
            fList[a]=s*pol
            if (s * pol == 0):
                fList[a]=1
        if k in hygDict.keys():
            a=hygDict[k]
            hygList[a]=s*pol
            if (s * pol == 0):
                hygList[a]=s*pol
        if k in costDict.keys():
            a=costDict[k]
            costList[a]=s*pol
            if (s * pol == 0):
                costList[a]=1
        ########can avoid this --calculation without model####################
        if(d[0] in ambianceW):
            ambScore+=s*pol
        if (d[0] in foodW):
            fooScore += s*pol
        if (d[0] in hygieneW):
            hygScore += s*pol
        if (d[0] in serviceW):
            serScore += s*pol
        if (d[0] in costW):
            cosScore += s*pol
    newList.append(ambScore)
    newList.append(fooScore)
    newList.append(hygScore)
    newList.append(serScore)
    newList.append(cosScore)
    ##################Translation########################
    if(len(fullModelFeature)<900):
     fullModelFeature.append(featureList)
     fNewList.append(newList)
    else:
        newF.append(featureList)
        test.append(newList)
    fullModelAmbiance.append(ambList)
    fullModelCost.append(costList)
    fullModelFood.append(fList)
    fullModelHygiene.append(hygList)
    fullModelService.append(sList)
    ambianceScore.append(ambScore)
    foodScore.append(fooScore)
    serviceScore.append(serScore)
    hygieneScore.append(hygScore)
    costScore.append(cosScore)

  total=[]
  ###################just for printing purpose###############################
  # for i in range(len(reviewList)):
  #   countTot=0
  #   print("Review : ")
  #   print(reviewList[i])
  #   print("Human Interpretation : " + str(ratingList[i]))
  #   print("Ambiance :" + str(scoreCal(ambianceScore[i])))
  #   print("Food :" + str(scoreCal(foodScore[i])))
  #   print("Cost :" + str(scoreCal(costScore[i])))
  #   print("Service :" + str(scoreCal(serviceScore[i])))
  #   print("Hygiene :" + str(scoreCal(hygieneScore[i])))
  #   sum=(ambianceScore[i]+foodScore[i]+costScore[i]+hygieneScore[i]+serviceScore[i])
  #   total.append(sum)
  #   print("Average Rating :"+ str(scoreCal(sum)))

  #allFeatures=np.array(fullModelFeature)
  ambianceFeatures=np.array(fullModelAmbiance)
  costFeatures=np.array(fullModelCost)
  foodFeatures=np.array(fullModelFood)
  serviceFeatures=np.array(fullModelService)
  hygieneFeatures=np.array(fullModelHygiene)

  # print(allFeatures)
  # print(ambianceFeatures)
  # print(costFeatures)
  # print(foodFeatures)
  # print(serviceFeatures)
  # print(hygieneFeatures)
  y=np.array(ratingList[0:900])
  yt=np.array(ratingList[900:1000])
  l=len(np.array(fNewList))
  l1=len(np.array(nounList))
  l2 = len(np.array(verbList))
  print(l)
  print(l1)
  print(l2)
  finalList=[]
  for j in range(len(nounList)):
      a=[]
      a.append(nounList[j])
      v = verbList[j].replace("\n", "")
      a.append(v)
      finalList.append(a)

  finalTestList = []
  for j in range(len(nounTestList)):
      a = []
      v=verbTestList[j].replace("\n","")
      #v=float(v)
      a.append(nounTestList[j])
      a.append(v)
      finalTestList.append(a)

  print("lengths"+str(np.hstack((fNewList,finalList))))
  #print(allFeatures)
  clf1 = MLPClassifier(activation='relu', solver='lbfgs', alpha=0.00001, hidden_layer_sizes=(45, 10,), max_iter=30)
  clf1.fit(np.hstack((np.hstack((np.float64(fNewList),np.float64(finalList))),np.hstack((np.float64(trainVerb),np.float64(trainPunc))))), np.array(y))
  y_pred = clf1.predict(np.hstack((np.hstack((np.float64(test),np.float64(finalTestList))),np.hstack((np.float64(testVerb),np.float64(testPunc))))))
  accuracy = accuracy_score( yt, y_pred)
  print(y_pred)
  print(accuracy)

def sent_vectorizer(sent, model):
  sent_vec = []
  numw = 0
  for w in sent:
    try:
      if numw == 0:
        sent_vec = model[w]
      else:
        sent_vec = np.add(sent_vec, model[w])
      numw += 1
    except:
      pass

  return np.divide(sent_vec,numw)
def scoreCal(val):
    y=((val-1)*(4/30))+1
    return y




if __name__ == "__main__": main()