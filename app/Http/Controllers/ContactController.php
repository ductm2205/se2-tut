<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Contact;

class ContactController extends Controller
{
    //
    public function index()
    {
        $contacts = Contact::all();
        return view('contacts.index', compact('contacts'));
    }
    public function create()
    {
        return view('contacts.create');
    }
    public function store(Request $request)
    {
        $request->validate([
            'name' => 'required|string',
            'phone' => 'required|string',
        ]);
        Contact::create($request->only('name', 'phone'));
        return redirect()->route('contacts.index')->with('success', 'Contact added successfully!');
    }

}
