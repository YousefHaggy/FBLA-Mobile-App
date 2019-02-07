import os
import re
import io
questionRE=re.compile('\d\W|\d\d\W');
choicesRE=re.compile('[abcdABCD][.)]');
questionList=[]
answerList=[]
fileName="Securities & Investments:Sample Practice.txt"
class Question:
	def __init__(self,Question,TestName,CategoryName,QuestionChoices):
		self.Question=Question;
		self.TestName=TestName;
		self.CategoryName=CategoryName
		self.QuestionChoices=QuestionChoices
		self.CorrectAnswer=""
def scrape(textFile):
	categoryName="Securities & Investments"
	testName="Sample Practice"
	with open(textFile) as f:
		lookingForChoices=False
		lookingForAnswers=False
		question=""
		choices=[]
		for line in f:
			if lookingForAnswers and questionRE.match(line):
				answerList.append(line[questionRE.match(line).end():])
			elif questionRE.match(line) and not lookingForChoices:
				question=line[questionRE.match(line).end():]
				lookingForChoices=True;
			elif lookingForChoices:
				if choicesRE.match(line):
					choices.append(line[choicesRE.match(line).end():])
					if len(choices) ==4:
						lookingForChoices=False;
						questionList.append(Question(question,testName,categoryName,choices))
						question=""
						choices=[]
			if "ANSWER KEY" in line:
				lookingForAnswers=True;
scrape(fileName)
for i in range(len(questionList)):
	questionList[i].CorrectAnswer=answerList[i];
for q in questionList:
	answerIndex=0;
	choiceSTR=""
	print(q.CorrectAnswer);
	if "A" in q.CorrectAnswer:
		answerIndex=0
	elif "B" in q.CorrectAnswer:
		answerIndex=1
	elif "C" in q.CorrectAnswer:
		answerIndex=2
	elif "D" in q.CorrectAnswer:
		answerIndex=3

	q.CorrectAnswer=q.QuestionChoices[answerIndex];
	for c in q.QuestionChoices:
		choiceSTR+=c.strip()+";"
		q.QuestionChoices=choiceSTR
		#print(choiceSTR)
with open(fileName+".csv","w+") as f:
	for q in questionList:
		f.write(q.Question.strip()+"@"+q.TestName+"@"+q.CategoryName+"@"+q.QuestionChoices+"@"+q.CorrectAnswer.strip()+"\n")
	f.close()