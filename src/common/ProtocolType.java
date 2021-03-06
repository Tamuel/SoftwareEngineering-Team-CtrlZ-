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
	SET_REFRESH, // 새로운 과제를 생성했을 때 클라이언트 리프래시
	EDIT_ASSIGNMENT, // 교수가 과제 수정 시
	SUBMIT_ASSIGNMENT, // 학생이 교수가 생성한 과제에 과제 제출
	STUDENT_EDIT_ASSIGNMENT, // When Student Edit Assignment
	APPRAISAL_ASSIGNMENT, // 학생이 제출한 과제를 교수가 채점
	DELETE_ANSWER, // when delete answer
	MAKE_QUESTION, // 질문 생성 시
	EDIT_QUESTION,
	MAKE_ANSWER, // 답변 생성 시
	CLEAR_NOTICE, // when notices are checked
	NEED_REFRESH,
	REFRESH,
	ELSE
}