<h2>Springboot 2.0 Rest Api Template Project</h2>
<p>This is a template project for REST APIs that already has a minimum, needed to launch the project</p>
<p>It has the following: </p>
<ul>
    <li>User entity with roles (already mapped)</li>
    <li>Owned abstract class for every user owned entity you may need</li>
    <li>The corresponding User repository class (data-jpa used)</li>
    <li>The corresponding User service class</li>
    <li>Generic service class</li>
    <li>Generic service class for Owned entities</li>
    <li>JWT authorization</li>
    <li>Minimum Data Transfer Objects: </li>
        <ul>
            <li>Api response to send messages at request</li>
            <li>JWT authorization response to send authorization token on singin request</li>
            <li>Login DTO to receive login requests</li>
            <li>Singup DTO to receive signup requests</li>
            <li>Profile DTO to send User profile data without odd info</li>
        </ul>
    <li>User singup and singin controllers</li>
    <li>User email and nickname availability check controllers</li>
</ul>
<p>There are the following endpoints:</p>
<ul>
    <li>Signup endpoint: 
    <pre>/api/auth/signup</pre>
    POST request, consumes a json object:
    <pre>
        {
            "email"         : "any valid email",
            "password"      : "at least, 5 characters",
            "nickname"      : "any unique nickname, 3 - 25 characters",
            "firstName"     : "minimum 3 characters",
            "lastName"      : "minimum 3 characters",
            "description"   : "any description you want, may be empty",
            "avatarUrl"     : "a valid URL containing your avatar"
        }
    </pre>
    Has the following response (in case of success):
    <pre>
        {
            "success": true,
            "message": "User successfully registered!"
        }
    </pre>
    </li>
    <li>Signin endpoint: 
        <pre>/api/auth/signin</pre>
        POST request, consumes a json object:
        <pre>
            {
            	"authName": "email or nickname",
            	"password": "a valid password"
            }
        </pre>
        Has the following response (in case of success):
        <pre>
            {
                "token" : "a generated JWT token"
                "type"  : "Bearer"
            }
        </pre>
     </li>
    <li>
        Check mail availability endpoint:
        <pre>/api/check-email-available?email=any valid email, without quotes</pre>
        GET request, has the following response if email is available:
        <pre>
            {
                "success": true,
                "message": "Email available"
            }
        </pre>
        and the following if already registered
        <pre>
            {
                "success": false,
                "message": "Email already exists"
            }
        </pre>
    </li>
    <li>
        Check nickname availability endpoint:
        <pre>/api/check-nickname-available?nickname=any nickname, without quotes</pre>
        GET request, has the following response if nickname is available:
        <pre>
            {
                "success": true,
                "message": "Nickname available"
            }
        </pre>
        and the following if already registered:
        <pre>
            {
                "success": false,
                "message": "Nickname already exists"
            }
        </pre>
     </li>
</ul>
<p>Enjoy :)</p>