<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css" />
</head>
<body>
	<div class="container">


		<table>
			<c:if test="${errormessage != null ||successmessage!=null }">
				<tr >
					<th class="messagefield" colspan="6"><span class="success">${successmessage}</span><br>
					<span class="error"> ${errormessage}</span></th>
				</tr>
			</c:if>
			<tr>
				<td colspan="6"><a
					href="${pageContext.request.contextPath}/controller?command=gotoaddperson">Добавить
						запись</a></td>
			</tr>
			<tr>
				<th>Фамилия</th>
				<th>Имя</th>
				<th>Отчество</th>
				<th colspan=3>Телефон(ы)</th>

			</tr>

			<c:forEach var="num" items="${persons}">
				<tr>
					<td>${num.value.getSurname()}</td>
					<td>${num.value.getName()}</td>
					<td>${num.value.getMiddlename()}</td>

					<td><c:forEach var="phone" items="${num.value.getPhones()}">
						${phone.value}<br>
						</c:forEach></td>
					<td><a
						href="${pageContext.request.contextPath}/controller?command=gotoeditperson&id=${num.value.getId()}">Редактировать</a></td>
					<td><a
						href="${pageContext.request.contextPath}/controller?command=deleteperson&id=${num.value.getId()}">Удалить</a></td>
				</tr>
			</c:forEach>

		</table>
	</div>
</body>
</html>