package com.example.examenandroid.Clases;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class GuardarContactos extends Application {
        private List<Contacto> contactos = new ArrayList<>();

        public List<Contacto> getContactosList() {
            return contactos;
        }
        public void setContactosList(Contacto contacto) {
            contactos.add(contacto);
        }
}
