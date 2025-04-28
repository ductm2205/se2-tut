@extends('layouts.contact')
@section('content')
    <div class="container">
        <h2>Contact List</h2>
        @if(session('success'))
            <div style="color: green">{{ session('success') }}</div>
        @endif
        <ul>
            @foreach($contacts as $contact)
                <li>{{ $contact->name }} - {{ $contact->phone }}</li>
            @endforeach
        </ul>
        @auth
            <a href="{{ route('contacts.create') }}">Add New Contact</a>
        @endauth
    </div>
@endsection