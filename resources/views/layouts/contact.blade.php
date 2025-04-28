<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Contacts App</title>
    <link href="{{ asset('css/app.css') }}" rel="stylesheet">
    @vite(['resources/css/app.css', 'resources/js/app.js'])
</head>

<body class="antialiased">
    <nav style="background-color: #f3f4f6; padding: 10px;">
        <a href="{{ route('contacts.index') }}">Home</a>
        @auth
                    <a href="{{ route('contacts.create') }}" style="margin-left: 10px;">Add
                        Contact</a>
                    <form method="POST" action="{{ route('logout') }}" style="display:
            inline; margin-left: 10px;">
                        @csrf
                        <button type="submit">Logout</button>
                    </form>
        @else
                    <a href="{{ route('login') }}" style="margin-left: 10px;">Login</a>
                    <a href="{{ route('register') }}" style="margin-left:
            10px;">Register</a>
        @endauth
    </nav>
    <div class="container" style="padding: 20px;">
        @yield('content')
    </div>
</body>

</html>