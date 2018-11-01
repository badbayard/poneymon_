package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;

public class ServerMultiController extends Controller {
    /**
     * Accesseur à fieldModel permettant au Seveur de récupérer l'objet et l'envoyer aux clients.
     *
     * @return le fieldModel tournant sur le serveur
     */
    public FieldModel getFieldModel() {
        return fieldModel;
    }
}
