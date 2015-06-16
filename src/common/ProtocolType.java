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
	SET_REFRESH, // ���ο� ������ �������� �� Ŭ���̾�Ʈ ��������
	EDIT_ASSIGNMENT, // ������ ���� ���� ��
	SUBMIT_ASSIGNMENT, // �л��� ������ ������ ������ ���� ����
	STUDENT_EDIT_ASSIGNMENT, // When Student Edit Assignment
	APPRAISAL_ASSIGNMENT, // �л��� ������ ������ ������ ä��
	MAKE_QUESTION, // ���� ���� ��
	MAKE_ANSWER, // �亯 ���� ��
	NEED_REFRESH,
	REFRESH,
	ELSE
}