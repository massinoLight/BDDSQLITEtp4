package com.example.bddsqlitetp4

/*
* le code ci-dessous contient une classe permettant de créer/mettre a jours la bdd
* et permet egalement de récupérer un objet
* qui permet la connexion a la bdd
*
* */

import  android.database.sqlite.SQLiteDatabase
import  android.database.sqlite.SQLiteOpenHelper
import android.content.Context
import com.example.bddsqlitetp4.Contracts
import org.jetbrains.anko.db.*

val Context.database : BddHelper
    get() = BddHelper.getInstance(applicationContext)


class  BddHelper(ctx: Context):ManagedSQLiteOpenHelper(ctx, DATABASE_NAME,null,1){


    companion object{
        const val DATABASE_NAME = "BddPersonne.db"


        private var instance:BddHelper?=null

        @Synchronized
        fun getInstance(ctx:Context):BddHelper{
            if (instance==null) {
                instance = BddHelper(ctx)
            }
            return  instance as BddHelper

        }

    }


    override fun onCreate(db: SQLiteDatabase?) {
        //Permet e creer la table personnes
        db?.createTable(Contracts.Personne.TABLE_NAME,false,
            Contracts.Personne.COLUM_NOM to TEXT + PRIMARY_KEY,
            Contracts.Personne.COLUM_EMAIL to TEXT,
            Contracts.Personne.COLUM_TEL to TEXT,
            Contracts.Personne.COLUM_FIXE to TEXT)

 }




    //permet de mettre a jour la table personnes
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
      db?.dropTable(Contracts.Personne.TABLE_NAME,true)
      onCreate(db)

    }





}
