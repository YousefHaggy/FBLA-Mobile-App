import os
import re
import io
class Question:
	def __init__(self,Question,TestName,CategoryName,QuestionChoices):
		self.Question=Question;
		self.TestName=TestName;
		self.CategoryName=CategoryName
		self.QuestionChoices=QuestionChoices
		self.CorrectAnswer=""
questionList=[]
fileName="Economics_2012 Test.txt"
document=""
with open(fileName) as f:
	for line in f:
		document+=line
	f.close()
trueOrFalse=document.split("Multiple Choice")[0];
multipleChoice=document.split("Multiple Choice")[1].split("Answer Section")[0]
answerSection=document.split("Answer Section")[1]
trueOrFalseAnswer=answerSection.split("MULTIPLE CHOICE")[0]
for i in trueOrFalse.split("____"):
	questionList.append(Question(i.split(".",1)[1].replace("\n","").strip(),"Economics 2012 Test","Economics","True;False"))
for i in range(len(trueOrFalseAnswer.split("\n"))):
	if trueOrFalseAnswer.split("\n")[i].split("ANS:")[1].strip() =="T":
		questionList[i].CorrectAnswer="True"
	else:
		questionList[i].CorrectAnswer="False"
for i in re.split("[_][_][_][_](?![.])",multipleChoice):
	baseQuestion=i.split(".",1)
	brokenFurther=re.split("(?<=\n)[abcd][.]\n",baseQuestion[1])
	for c in range(len(brokenFurther)):
		brokenFurther[c]=brokenFurther[c].replace("\n","")
	print(brokenFurther)
