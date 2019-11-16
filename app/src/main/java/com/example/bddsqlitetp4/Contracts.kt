package com.example.bddsqlitetp4

import  android.provider.BaseColumns

/*
* le code ci-dessous contien une classe avec les valeurs utiles pour construire
* et manipuler la table Personne en SQL
*
* */
object Contracts {

    object Personne:BaseColumns{
        const val TABLE_NAME="TablePersonne"
        const val  COLUM_NOM="nom"
        const val  COLUM_EMAIL="email"
        const val  COLUM_TEL="tel"
        const val  COLUM_FIXE="fixe"
        const val SQL_CREATE_TABLE=
                                 "CREATE TABLE ${TABLE_NAME} ("+
                                         "${COLUM_NOM} TEXT,"+
                                         "${COLUM_EMAIL} TEXT,"+
                                         "${COLUM_TEL} TEXT,"+
                                         "${COLUM_FIXE} TEXT,"

        const val SQL_DELETE_TABLE="DROP TABLE IF EXISTS ${Personne.TABLE_NAME}"

    }


}