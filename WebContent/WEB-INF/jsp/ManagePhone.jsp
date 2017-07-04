<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Управление телефонными данными</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css" />
</head>
<body>
	<div class="container">

		<form action="controller" method="post">
			<input type="hidden" name="command" value="editphone" />
			<c:if test="${phoneid != null}">
				<c:set var="jsp_parameters_label" value="${\"Сохранить\"}" />
			</c:if>
			<c:if test="${phone == null}">
				<c:set var="jsp_parameters_label" value="${\"Добавить\"}" />
			</c:if>
			<table style="width: 75%">
				<c:if test="${errormessage != null ||successmessage!=null }">
					<tr>
						<td colspan="6"><span class="success">${successmessage}</span>
							<span class="error"> ${errormessage}</span></td>
					</tr>
				</c:if>
				<tr>
					<th colspan="2" align="center">Информация о телефоне
						владельца: ${person.toString()}</th>
				</tr>
				<tr>
					<td style="width: 25%">Номер:</td>
					<td><input type="text" name="phone" value="${phone}" /></td>
				</tr>
				<tr>
					<th colspan="2" align="center"><input type="submit"
						value="${jsp_parameters_label}" /> <br> <br> <a
						href="${pageContext.request.contextPath}/controller?command=gotoaddperson">Вернуться
							к данным о человеке</a></th>
				</tr>

			</table>
		</form>
	</div>
</body>
</html>