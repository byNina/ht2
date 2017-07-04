<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Управление данными о человеке</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css" />
</head>
<body>
	<div class="container">
		<form action="controller" method="post">
			<c:if test="${person == null}">
				<input type="hidden" name="command" value="addperson" />
				<c:set var="jsp_parameters_label" value="${\"Добавить\"}" />
			</c:if>
			<c:if test="${person != null}">
				<input type="hidden" name="command" value="editperson" />
				<input type="hidden" name="id" value="${person.getId()}" />
				<c:set var="jsp_parameters_label" value="${\"Сохранить\"}" />
			</c:if>
			<table style="width: 75%">
				<c:if test="${errormessage != null ||successmessage!=null }">
					<tr>
						<td colspan="6"><span class="success">${successmessage}</span>
							<span class="error"> ${errormessage}</span></td>
					</tr>
				</c:if>
				<tr>
					<th colspan="2" align="center">Информация о человеке</th>
				</tr>
				<tr>
					<td style="width: 25%">Фамилия:</td>
					<td><input type="text" name="surname"
						value="${person.getSurname() }" /></td>
				</tr>
				<tr>
					<td>Имя:</td>
					<td><input type="text" name="name"
						value="${person.getName() }" /></td>
				</tr>
				<tr>
					<td>Отчество:</td>
					<td><input type="text" name="middlename"
						value="${person.getMiddlename()}" /></td>
				</tr>
				<c:if test="${person != null}">
					<tr>
						<td>Телефоны:</td>
						<td><c:forEach var="phone" items="${person.getPhones()}">
						<div>
								<c:out value="${phone.value}"></c:out>
								<div class="menu">
									<a
										href="${pageContext.request.contextPath}/controller?command=gotoPhoneManager&phoneid=${phone.key}&phone=${phone.value}">Редактировать</a>

									<a
										href="${pageContext.request.contextPath}/controller?command=deletePhone&phoneid=${phone.key}">Удалить</a>
								</div></div>
								<br>
							</c:forEach> <a
							href="${pageContext.request.contextPath}/controller?command=gotoPhoneManager">Добавить
								телефон</a></td>
					</tr>
				</c:if>
				<tr>
					<th colspan="2" align="center"><input type="submit"
						value="${jsp_parameters_label}" /> <br> <br> <a
						href="${pageContext.request.contextPath}/controller?command=showall">Вернуться
							к списку</a></th>
				</tr>

			</table>
		</form>
	</div>
</body>
</html>