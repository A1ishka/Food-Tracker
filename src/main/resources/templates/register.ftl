<!DOCTYPE html>
<html>
<head>
    <title>Registration Form</title>
</head>
<body>
<h1>Registration Form</h1>
<form action="/register" method="post">
    <label for="login">Login:</label>
    <input type="text" id="login" name="login" required><br>

    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required><br>

    <input type="submit" value="Register">
</form>
</body>
</html>