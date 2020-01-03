package com.example.sleepexpert

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ListItemTips : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val bundle: Bundle? = intent.extras
        var id = 0
        if (bundle != null)
            id = bundle.getInt("key")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_item_tips)

        val buttonBack = findViewById<Button>(R.id.buttonBackToTips)
        buttonBack.setOnClickListener {
            startActivity(Intent(this, Tips::class.java))
        }

        val title = findViewById<TextView>(R.id.textViewListItemTitle)
        title.text = getItemTitle(id)
        val content = findViewById<TextView>(R.id.textViewListItemContent)
        content.text = getItemContent(id)

    }

    private fun getItemTitle(id: Int): String {
        var title: String
        when (id) {
            0 -> title = "Rituale schaffen"
            1 -> title = "Probleme aus dem Bett verbannen"
            2 -> title = "Weg mit den Schäfchen!"
            3 -> title = "Leise Töne für entspanntes Einschlafen"
            4 -> title = "In den Schlaf atmen"
            5 -> title = "Ein wenig Konzentration"
            6 -> title = "Schalten Sie einfach mal ab!"
            7 -> title = "Cool bleiben, wenn die Hormone verrücktspielen"
            else -> { // Note the block
                title = "doesnt exist"
            }
        }
        return title
    }
    private fun getItemContent(id: Int): String {
        var content: String
        when (id) {
            0 -> content = "Was kleinen Kindern zum Einschlafen hilft, kann auch Erwachsenen nicht schaden: Schaffen Sie sich Ihr persönliches Einschlafritual, mit dem Sie sich auf die Nacht vorbereiten. Ob die abendliche Pflege, etwas lesen, mit dem Partner beziehungsweise dem Haustier kuscheln — ruhige Tätigkeiten stimmen Sie auf die Nacht ein. Wichtig: Versuchen Sie, jeden Abend die gleichen Schritte durchzugehen. Dann gewöhnt sich Ihr Körper an den Rhythmus und Sie werden automatisch müde."
            1 -> content = "Das Wälzen von Problemen kann uns schon mal das Einschlafen erschweren. Deshalb haben Probleme im Bett nichts zu suchen! Wenn Sie sich beim Grübeln ertappen, stehen Sie auf und setzen Sie sich an einen Ort in Ihrer Wohnung, an dem Sie für gewöhnlich arbeiten. Dort schreiben Sie auf einen Zettel, was Sie beschäftigt. Lassen Sie Ihre Sorgen auf dem Tisch liegen und gehen Sie wieder ins Bett. So haben Sie nämlich all Ihren Kummer aufgespürt, benannt und aufgeschrieben. Auf dem Blatt Papier können diese Gedanken dann bis morgen auf Sie warten."
            2 -> content = "Schäfchenzählen bringt nicht immer Erfolg — denn dabei kann man immer noch über Probleme grübeln. Besser: eine anspruchsvollere Aufgabe, auf die Sie sich ein bisschen konzentrieren müssen, wie beispielsweise in Sechserschritten vorwärts zählen oder von der Zahl 1.000 in Dreierschritten rückwärts."
            3 -> content = "Ruhige Musik, wie Balladen oder Klassik, kann wahre Wunder bewirken. Drehen Sie die Musik so leise, dass Sie sie gerade noch hören können. So müssen Sie sich etwas konzentrieren, und störende Gedanken oder Grübeleien haben keine Chance."
            4 -> content = "Mit dieser einfachen Übung vertreiben Sie schlechte Gedanken und entspannen herrlich: Begeben Sie sich in die Rückenlage und legen Sie eine Hand auf den Bauch. Konzentrieren Sie sich auf Ihre Atmung. Sagen Sie beim Ein- und Ausatmen leise „ein, aus, ein, aus“ und sprechen Sie beim Ausatmen ein langes „s“. Sie atmen übrigens tiefer und entspannter, wenn sich beim Einatmen erst der Bauch hebt und danach die Brust."
            5 -> content = "Es will mit dem Einschlafen einfach nicht klappen? Dann wälzen Sie sich nicht im Bett hin und her, sondern stehen Sie auf. Erledigen Sie eine ruhige Aufgabe, die Sie jederzeit unterbrechen können und auf die Sie sich ein wenig konzentrieren müssen, wie beispielsweise ein Kreuzworträtsel lösen oder stricken. Machen Sie diese Tätigkeit so lange, bis Sie sich schläfrig fühlen — danach gehen Sie wieder ins Bett."
            6 -> content = "Wir wissen genau, wie abgedroschen es klingt: Aber legen Sie abends das Smartphone weg! Wie schädlich das blaue Bildschirmlicht unserer heiß geliebten Handys, Tablets und Laptops wirklich ist, merken wir später im Bett — da nützen dann auch alle Einschlafhilfen nichts mehr. Denn dieses künstliche Licht stört die Produktion des schlaffördernden Hormons Melatonin und das erschwert uns das Einschlafen. Es gibt aber noch ein Hintertürchen: Wenn Sie auch vor dem Zubettgehen nicht auf Ihr Smartphone verzichten möchten, verwenden Sie eine App, die einen Rotfilter über Ihren Bildschirm legt. Studien haben gezeigt, dass Menschen mit dem roten Filter zweimal weniger pro Nacht aufwachen als mit dem blauen Licht.\n"
            7 -> content = "Während unserer Periode steigt die Körpertemperatur an — was oft den Effekt hat, dass wir nachts schwitzen. Doch nicht nur das: Wir leiden unter Krämpfen und unangenehmen Wassereinlagerungen. Nicht gerade eine vorteilhafte Kombination für einen guten Schlaf! Halten Sie daher Ihr Schlafzimmer kühl und haben Sie immer ein Glas Wasser neben dem Bett stehen. Tragen Sie Baumwollnachtwäsche und verwenden Sie auch Bettlaken aus Baumwolle. Bettwäsche aus Wolle eignet sich ebenfalls sehr gut, denn nachweislich reguliert Wollfaser die Temperatur viel besser als andere Stoffe."
            else -> { // Note the block
                content = "doesnt exist"
            }
        }
        return content
    }

}

