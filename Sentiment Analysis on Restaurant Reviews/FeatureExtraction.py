import os
from nltk import pos_tag
from nltk import word_tokenize
from afinn import Afinn
import csv
from sklearn.feature_extraction.text import CountVectorizer
import numpy as np
from sklearn.neural_network import MLPClassifier
from sklearn.neural_network import MLPRegressor
from sklearn.model_selection import cross_val_score
from gensim.models import KeyedVectors
import warnings
from sklearn import svm
from nltk.corpus import stopwords

warnings.filterwarnings("ignore")
import matplotlib.pyplot as plt

neurons=50
scores_relu=[1]
plt.plot(neurons,scores_relu)

plt.legend(['relu','linear','logistic','tanh'])
plt.xlabel("number of neurons in 1st hidden layer")
plt.ylabel("cross-validate recall")
plt.show()

#model = KeyedVectors.load_word2vec_format('Word2Vec/GoogleNews-vectors-negative300.bin.gz', binary=True)

##########################################################################################
# loading nearby words for 5 features
# ambiance-234 words
# service-707 words
# cost-641 words
# food-662 words
# hygiene-355 words
##########################################################################################
def main():

  # Specify the number of records you are interested in
  record_count=1000

  afinn = Afinn(emoticons=True)
  #print(afinn.score("'Milkweed Cafe' ambiance is really nice :(."))
  hygieneW=[]
  f1 = open( "yelp/hygiene.txt", "r",encoding='utf-8')
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
  f4= open( "yelp/service.txt", "r",encoding='utf-8')
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
  vectorizer = CountVectorizer()
  businessIDList=[]
  ratingList=[]
  reviewList=[]
  path = "yelp/"

  with open( path+'business_review.csv',encoding='latin') as csvfile:
    readCSV = csv.reader(csvfile, delimiter=',')
    for row in readCSV:

        ########for rows with no data############
        if (row[0] == ""):
            continue

        businessIDList.append(row[1])
        ratingList.append(row[2])
        r=row[3]
        r=r.replace("\n"," ")
        r=r.replace(" +"," ")
        reviewList.append(r)
        #print(r)
        if(len(reviewList)==record_count): break

    X = vectorizer.fit_transform(reviewList)

    print('cv',X.toarray())


    # getting all NA pairs in corpus
    V1 = []
    # for sentence in reviewList:
    #     words=sentence.split(" ")
    #     c = sent_vectorizer(word_tokenize(sentence), model)
    #     # if(len(V1)<5800):
    #     V1.append(c)
    #     # else: V2.append(c)
    #     if(len(V1)==record_count):break
    V =np.array(V1)

  ##############################################################################################################
  # getting feature lists for ambiance, food, cost, service and hygiene and fullfeatureList
  ##############################################################################################################
  # getting all NA pairs in corpus
  nounList = []
  NAPair=[]
  naFullFeature=[]
  with open(path + 'noun-adjective-50000-reviews.csv',encoding='latin') as csvfile:
    readCSV = csv.reader(csvfile, delimiter=',')
    n_count=1
    for row in readCSV:

        pairs = []
        if(len(row)!=0):
            nounList.append([len(row)])
            for i in row:
                pairs.append(i)
                if i not in naFullFeature:
                    naFullFeature.append(i)
            #print(pairs)
            NAPair.append(pairs)
            if(len(NAPair))==record_count:break

  print('nounList',nounList)

  #####################################################################################################
  verbList = []
  verbTestList = []
  with open(path + 'verbs-1000.csv', encoding='latin') as csvfile:
      readCSV = csv.reader(csvfile, delimiter=',')
      count = 1
      for row in readCSV:

          ########for rows with no data############

          if (count % 2 == 1):

                  verbList.append([len(row)])
                  if len(verbList)==record_count:break

          count += 1
  print('verb_list',verbList)

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
  for j in NAPair:
    #print(j)
    featureList = []
    ambList=[]
    sList=[]
    hygList=[]
    costList=[]
    fList=[]

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
        if k in ambDict.keys():
            a=ambDict[k]
            ambList[a]=s*pol
        if k in serDict.keys():
            a=serDict[k]
            sList[a]=s*pol
        if k in foodDict.keys():
            a=foodDict[k]
            fList[a]=s*pol
        if k in hygDict.keys():
            a=hygDict[k]
            hygList[a]=s*pol
        if k in costDict.keys():
            a=costDict[k]
            costList[a]=s*pol
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
    ##################Translation########################
    fullModelFeature.append(featureList)
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

  # Creating  a new X vector here
  X_list=[]
  ###################just for printing purpose###############################
  for i in range(len(reviewList)):
    countTot=0
    print("Review : ")
    print(reviewList[i])
    print("Human Interpretation : " + str(ratingList[i]))
    print("Ambiance :" + str(scoreCal(ambianceScore[i])))
    print("Food :" + str(scoreCal(foodScore[i])))
    print("Cost :" + str(scoreCal(costScore[i])))
    print("Service :" + str(scoreCal(serviceScore[i])))
    print("Hygiene :" + str(scoreCal(hygieneScore[i])))
    sum=(ambianceScore[i]+foodScore[i]+costScore[i]+hygieneScore[i]+serviceScore[i])
    total.append(sum)
    a=sum
    b=(scoreCal(ambianceScore[i]))
    c=(scoreCal(foodScore[i]))
    d=(scoreCal(costScore[i]))
    e=(scoreCal(serviceScore[i]))
    f=(scoreCal(hygieneScore[i]))
    X_list.append([c,d,e])

    #print('0',ratingList[0])

  allFeatures=np.array(fullModelFeature)
  ambianceFeatures=np.array(fullModelAmbiance)
  costFeatures=np.array(fullModelCost)
  foodFeatures=np.array(fullModelFood)
  serviceFeatures=np.array(fullModelService)
  hygieneFeatures=np.array(fullModelHygiene)
  # print('all',allFeatures)
  # print('amb',ambianceFeatures)
  # print('cos',costFeatures)
  # print('foo',foodFeatures)
  # print('ser',serviceFeatures)
  # print('hyg',hygieneFeatures)



  # We will try our experiments with features here

  a=np.array(X.toarray())# count vectorizer
  b=np.array(V)# word2vec
  X_list=np.hstack((X_list,a,nounList))



  # relu score [0.25074627 0.28228228 0.25903614] -- very low accuracy

  # on 100 data
  # relu score [0.31428571 0.5        0.38709677]
  # tanh score [0.28571429 0.35294118 0.5483871
  # tanh score [0.28571429 0.35294118 0.5483871

  # Feature- word2vec
  # relu score [-2.49369865 -0.30916311 -0.57445711] - logistic regression
  # tanh score [0.6        0.5        0.35483871]
  # identity score [0.48571429 0.41176471 0.32258065]

  # On 1000 data
  # relu score [0.32835821 0.37537538 0.37650602]
  # tanh score [0.3761194 0.3993994 0.4126506]
  # identity score [0.4        0.3993994  0.40361446]

  # Feature- word2vec
  # relu score [-0.2850449  -0.13615922 -0.16395872]
  # tanh score [0.4        0.41141141 0.40060241]
  # identity score [0.40298507 0.37237237 0.39759036]


  # On 10,000 data
  # relu score [0.32443778 0.33483348 0.33643457]
  # tanh score [0.33703148 0.34053405 0.3502401 ]
  # identity score [0.34062969 0.34083408 0.34843938]




  # Import models here

  # Classifier with lbfgs & relu layer. alpha=0.00000001, hidden layers (1000,10)
  reg=MLPClassifier(solver='lbfgs',activation='relu', alpha=1e-5,hidden_layer_sizes=(50,50,), random_state=1)
  #reg.fit(allFeatures,np.array(ratingList,dtype=float))
  score = cross_val_score(reg,np.array(X_list,dtype=float), np.array(ratingList,dtype=float), cv=5, scoring="accuracy")
  print('relu score',score)
  reg.fit(X_list, np.array(ratingList, dtype=float))


  # Classifier with lbfgs & tanh layer. alpha=0.0000001, hidden layers (1000,10)
  reg = MLPClassifier(solver='lbfgs', activation='tanh', alpha=1e-5, hidden_layer_sizes=(50, 30,),
                      random_state=1)
  clf = svm.SVC(kernel='precomputed')
  # reg.fit(allFeatures,np.array(ratingList,dtype=float))
  score = cross_val_score(reg, X_list, np.array(ratingList, dtype=float), cv=5, scoring="accuracy")
  print('tanh score',score)
  reg.fit(X_list, np.array(ratingList, dtype=float))



  # Classifier with lbfgs & identity layer. alpha=0.000000001, hidden layers (10000,10)
  reg = MLPRegressor(solver='lbfgs', activation='logistic', alpha=0.000000001, hidden_layer_sizes=(10000, 10,),
                      random_state=1)
  # reg.fit(allFeatures,np.array(ratingList,dtype=float))
  score = cross_val_score(reg, X_list, np.array(ratingList, dtype=float), cv=3, scoring="r2")
  print('identity score', score)
  reg.fit(X_list, np.array(ratingList, dtype=float))



# Calculate the score
def scoreCal(val):
    y=((val-1)*(4/30))+1
    return val


# Calculate average feature vector of the reviews
def sent_vectorizer(sent, model):
  stop_words = set(stopwords.words('english'))
  sent = [w for w in sent if not w in stop_words]
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




if __name__ == "__main__": main()