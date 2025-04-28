@extends('layouts.contact')
@section('content')
    <div class="container">
        <h2>Add Contact</h2>
        <form method="POST" action="{{ route('contacts.store') }}">
            @csrf
            <div>
                <label>Name:</label>
                <input type="text" name="name" required>
            </div>
            <div>
                <label>Phone:</label>
                <input type="text" name="phone" required>
            </div>
            <button type="submit">Save</button>
        </form>
    </div>
@endsection