<%-- 
    Document   : create
    Created on : Oct 13, 2024, 8:17:26 PM
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Dish</title>
        <link rel="stylesheet" href="css/createDish.css">
    </head>
    <body>
        <h3 style="color: red">${requestScope.error}</h3>

        <h1>Create a dish</h1>
        <form action="addDish" method="get">
            Enter name: <input type="text" name="name" value="" /> <br>
            Enter price: <input type="text" name="price" value="" /> <br>
            Enter status (yes/no): 
            <select name="status">
                <option value="yes" ${d.status == 'yes' ? 'selected' : ''}>Yes</option>
                <option value="no" ${d.status == 'no' ? 'selected' : ''}>No</option>
            </select>
            <br><br>
            Enter ImageLink : <input type="text" name="image" value="" /> <br>
            <input type="submit" value="Save" />
        </form>
    </body>
</html>
