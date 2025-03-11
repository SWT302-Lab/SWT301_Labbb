<%-- 
    Document   : createTable
    Created on : Oct 23, 2024, 10:20:44 PM
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <link rel="stylesheet" href="css/createTable.css">
        <title>Create New Table</title>
    </head>
    <body>
    <center>
        <h1>Create New Table</h1>
        <h3 style="color: red">${requestScope.error}</h3>
        
        
        <form action="addTable" method="post">
            <table>
                <tr>
                    <td>Table ID:</td>
                    <td><input type="text" name="id" required/></td>
                </tr>
               
                <tr>
                    <td><input type="submit" value="Create Table" /></td>
                </tr>
            </table>
        </form>

        <br/>
        
    </center>
    </body>
</html>
