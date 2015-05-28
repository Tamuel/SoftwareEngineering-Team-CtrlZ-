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
	MAKE_ASSIGNMENT, // ������ ���ο� ���� ����
	SUBMIT_ASSIGNMENT, // �л��� ������ ������ ������ ���� ����
	APPRAISAL_ASSIGNMENT, // �л��� ������ ������ ������ ä��
	MAKE_QUESTION, // ���� ���� ��
	MAKE_ANSWER, // �亯 ���� ��
	ELSE
}