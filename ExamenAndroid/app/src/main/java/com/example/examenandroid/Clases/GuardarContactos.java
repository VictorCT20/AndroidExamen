package com.example.examenandroid.Clases;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class GuardarContactos extends Application {
        private List<Paisaje> paisajes = new ArrayList<>();

        public List<Paisaje> getContactosList() {
            return paisajes;
        }
        public void setContactosList(Paisaje paisaje) {
            paisajes.add(paisaje);
        }
}
