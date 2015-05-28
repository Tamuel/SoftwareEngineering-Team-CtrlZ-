package common;

public enum ProtocolType {
	LOGIN,
	LOGIN_ACCEPT,
	LOGIN_FAIL,
	ID_EXIST,
	JOIN,
	JOIN_ACCEPT,
	REQUEST_ACCOUNT_LIST,
	ACCOUNT_LIST,
	ADD_SUBJECT,
	QUIT,
	MAKE_ASSIGNMENT, // 교수가 새로운 과제 생성
	SUBMIT_ASSIGNMENT, // 학생이 교수가 생성한 과제에 과제 제출
	APPRAISAL_ASSIGNMENT, // 학생이 제출한 과제를 교수가 채점
	MAKE_QUESTION, // 질문 생성 시
	MAKE_ANSWER, // 답변 생성 시
	ELSE
}