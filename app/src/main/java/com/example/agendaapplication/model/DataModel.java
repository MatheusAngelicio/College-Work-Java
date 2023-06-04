package com.example.agendaapplication.model;

import android.content.Context;

import java.util.ArrayList;

//Singleton
public class DataModel {
    private static DataModel instance = new DataModel();

    private DataModel() {

    }

    public static DataModel getInstance() {
        return instance;
    }

    private ArrayList<Contact> contacts;
    private ContactDataBase dataBase;


    public void createDatabase(Context context) {
        dataBase = new ContactDataBase(context);
        contacts = dataBase.getContactsFromDB();
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public Contact getContact(int position) {
        return contacts.get(position);
    }

    public int getContactsSize(){
        return contacts.size();
    }

    public boolean addContact(Contact c) {
        long id = dataBase.createContactInDB(c);
        // se criou um ID, quer dizer que deu sucesso
        if (id > 0) {
            c.setId(id);
            contacts.add(c);
            return true;
        }
        return false;
    }

    public boolean insertContact(Contact c, int position) {
        long id = dataBase.insertContactInDB(c);
        // se criou um ID, quer dizer que deu sucesso
        if (id > 0) {
            contacts.add(position, c);
            return true;
        }
        return false;
    }

    public boolean updateContact(Contact c, int position) {
        int count = dataBase.updateContactInDB(c);
        if (count == 1) {
            contacts.set(position, c);
            return true;
        }
        return false;
    }

    public boolean removeContact(int position) {
        int count = dataBase.removeContactInDB(
                getContact(position)
        );
        if (count == 1) {
            contacts.remove(position);
            return true;
        }
        return false;
    }

}
