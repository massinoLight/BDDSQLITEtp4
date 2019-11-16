package com.example.bddsqlitetp4



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.Activity
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import org.jetbrains.anko.db.asMapSequence
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
//import org.jetbrains.anko.toast
import java.io.File

class MainActivity : AppCompatActivity() {

    /*
    *
    * */


    var personnes = mutableListOf<Personne>()

    


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.bddsqlitetp4.R.layout.activity_main)


        //on recup le  contenu de la base de données a chaque lancement de l'application
        //ici le selectne prend pas de where car on recup l'integralité des tuples
        //on aurait pu ajouter un where dans le cas ou on a une bare de recherche par exemple
        // et que l'on souhaitait recuperer un contact en particulier
        //cette fonction et les fonctionns insert et update auraient pu etre dans une classe a part prenant en paramétre
        //les valeurs a insérer rechercher/mettre a jour
        database.use {
            select(Contracts.Personne.TABLE_NAME,
                Contracts.Personne.COLUM_NOM,
                Contracts.Personne.COLUM_EMAIL,
                Contracts.Personne.COLUM_TEL,
                Contracts.Personne.COLUM_FIXE).exec {
                for (row in asMapSequence()){
                    val Nom=row[Contracts.Personne.COLUM_NOM]as String
                    val Email=row[Contracts.Personne.COLUM_EMAIL]as String
                    val Tel=row[Contracts.Personne.COLUM_TEL]as String
                    val Fixe=row[Contracts.Personne.COLUM_FIXE]as String
                    var p9=Personne(Nom,Email,Tel,Fixe)
                    personnes.add(p9)
                    personnes.sortWith(compareBy({it.nom}))

                }

            }


        }

        buildRecyclerView()
        //le bouton pour permettre la saisie d'un contact
        btn_ajouter.setOnClickListener {

            startActivityForResult<AjoutPersonne>(1)


        }
    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            1 -> {
                // Résultat de startActivityForResult<ModifierActivity>
                if(resultCode == Activity.RESULT_OK){
                    val valider = data?.getBooleanExtra(AjoutPersonne.EXTRA_VALIDER, false) ?: false
                    if(valider){
                        // L'utilisateur a utilisé le bouton "valider"
                        // On récupère la valeur dans l'extra (avec une valeur par défaut de "")
                        val nouvValeurnom = data?.getStringExtra(AjoutPersonne.EXTRA_NOM) ?: ""
                        val nouvValeuremail = data?.getStringExtra(AjoutPersonne.EXTRA_EMAIL) ?: ""
                        val nouvValeurtel = data?.getStringExtra(AjoutPersonne.EXTRA_TEL) ?: ""
                        val nouvValeurfixe = data?.getStringExtra(AjoutPersonne.EXTRA_FAXE) ?: ""

                        var p8=Personne(nouvValeurnom,nouvValeuremail,nouvValeurtel,nouvValeurfixe)



                        personnes.add(0,p8)

                        //on insert dans la base de donnée
                        database.use {
                            insert(Contracts.Personne.TABLE_NAME,
                        Contracts.Personne.COLUM_NOM to p8.nom,
                        Contracts.Personne.COLUM_EMAIL to p8.email,
                        Contracts.Personne.COLUM_TEL to p8.tel,
                        Contracts.Personne.COLUM_FIXE to p8.fixe)
                    }


                        //toast("donnée inser dans la bdd ")

                        //cette ligne permet de trier la liste des contactes par ordre alphabetique
                        personnes.sortWith(compareBy({it.nom}))
                        buildRecyclerView()
                        mon_recycler.adapter?.notifyItemInserted(0)

                    }else{
                        //ID--
                    }
                }else if(resultCode == Activity.RESULT_CANCELED){
                    // L'utilisateur a utilisé le bouton retour <- de son téléphone
                    // on ne fait rien de spécial non plus
                }
            }
        }
    }


    fun buildRecyclerView() {
        mon_recycler.setHasFixedSize(true)
        //mon_recycler.setAdapter(mAdapter)
        mon_recycler.layoutManager = LinearLayoutManager(this)

        mon_recycler.adapter = PersonneAdapter(personnes.toTypedArray())
        {
            //ici on affiche juste toutes les informations dans un Toast
            //on aurait tres bien pu les passer en parametre avec un intent et les afficher dans une autre activity
            Toast.makeText(this, "Element selectionné: ${it}", Toast.LENGTH_LONG).show()
            var  nom="${it.nom}"
            var  tel="${it.tel}"
            var  mail="${it.email}"
            var  faxe="${it.fixe}"
            val intent3 = Intent(this, AfficheDetailActivity::class.java)
            intent3.putExtra("NOM",nom)
            intent3.putExtra("TEL",tel)
            intent3.putExtra("MAIL",mail)
            intent3.putExtra("FAXE",faxe)
            startActivity(intent3)


        }


    }



}