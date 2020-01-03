package com.example.sleepexpert

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ListItemKnowledge : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val bundle: Bundle? = intent.extras
        var id = 0
        if (bundle != null)
            id = bundle.getInt("key")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_item_knowledge)

        val buttonBack = findViewById<Button>(R.id.buttonBackToKnowledge)
        buttonBack.setOnClickListener {
            startActivity(Intent(this, Knowledge::class.java))
        }

        val title = findViewById<TextView>(R.id.textViewListItemTitle)
        title.text = getItemTitle(id)
        val content = findViewById<TextView>(R.id.textViewListItemContent)
        content.text = getItemContent(id)

    }

    private fun getItemTitle(id: Int): String {
        var title: String
        when (id) {
            0 -> title = "Was passiert im Schlaf?"
            1 -> title = "Warum ist ein guter Schlaf wichtig?"
            2 -> title = "Was geschieht beim Träumen?"
            3 -> title = "Wie wird man Albträume los?"
            4 -> title = "Wer schläft wie – und wie lang?"
            5 -> title = "Fragwürdige Schlaflosigkeit"
            6 -> title = "Im Pyjama oder nackt?"
            7 -> title = "Wofür ist das Gähnen gut?"
            8 -> title = "Was ist Schlafwandeln?"
            9 -> title = "Wieso knirschen Menschen im Schlaf mit den Zähnen?"
            else -> { // Note the block
                title = "doesnt exist"
            }
        }
        return title
    }
    private fun getItemContent(id: Int): String {
        var content: String
        when (id) {
            0 -> content = "Grundsätzlich ermöglicht Schlaf Erholung und Regeneration. Wichtige Körpervorgänge wie Muskelspannung, Blutdruck und Verdauungstätigkeit verändern sich je nach Schlafstadium. Sie werden heruntergefahren oder angekurbelt. Einige Körperzellen sind besonders aktiv und sorgen nachts für Zellwachstum und reparieren Schäden, die tagsüber entstanden sind. Gelenkt werden all diese Vorgänge vom Gehirn. Es schläft niemals."
            1 -> content = "Schlafen ist so lebenswichtig wie Trinken und Essen. Das Bedürfnis ändert sich mit dem Älterwerden: Während Jugendliche locker eine Nacht durchmachen können, spüren Erwachsene die Folgen einer langen Party am nächsten Tag. Dauerhafter Schlafmangel senkt die körperliche sowie die geistige Leistungsfähigkeit und schadet dem Immunsystem. Wissenschaftler vermuten, dass Schlafmangel auch zu Übergewicht beitragen kann."
            2 -> content = "Darüber, dass Träume für unsere Gesundheit wichtig sind, ist sich die Wissenschaft einig. Zur genauen physischen und psychischen Bedeutung allerdings gibt es verschiedene Theorien. Die plausibelste Theorie lautet: Träumen ist eine Art Aufräumaktion unseres Gehirns. Stimmt dieser Ansatz, ist er eine gute Nachricht für Studenten. Das Gehirn räumt in der Nacht nicht nur Erlebtes auf, sondern verarbeitet auch Gelerntes."
            3 -> content = "Böse Träume kommen nicht von der Alp, sondern von den Alben in der germanischen Mythologie, einer Art Elfen. Deshalb das b – wobei man auch «Alpträume» schreiben darf. Albträume entstehen während der REM-Schlafphase, in der zweiten Hälfte der Schlafenszeit. Experten raten dazu, sich mit Albträumen zu «konfrontieren», sie aufzuzeichnen oder aufzuschreiben, um sie dann mit einem neuen, positiven Ende versehen zu können."
            4 -> content = "Gemäss dem Bundesamt für Statistik leidet ein Viertel der Schweizer Bevölkerung unter Schlafstörungen. Acht von 100 Menschen geben an, Medikamente zu nehmen, um besser schlafen zu können. Schweizerinnen und Schweizer schlafen immer weniger; 7,5 Stunden zwischen Werktagen, 8,5 Stunden an Wochenenden. Vor rund 30 Jahren waren es pro Nacht noch 40 Minuten mehr. Katzen schlafen bis zu 16 Stunden am Tag. Enten schalten eine Hirnhälfte ab, Mauersegler können sogar im Flug schlafen."
            5 -> content = "Den Rekord im freiwilligen Schlafentzug hält der Brite Tony Wright mit 266 Stunden. Einen Eintrag ins Guinnessbuch der Rekorde erhielt er aber nicht, weil ein Schlafentzug von mehr als vier Tagen als unethisch gilt – und aus gesundheitlicher Sicht bedenklich ist."
            6 -> content = "Forscher haben im Auftrag des Baumwollproduzenten Cotton USA 500 Paare zu ihren Schlafgewohnheiten befragt. Es stellte sich heraus, dass Paare, die nackt schlafen, offenbar glücklicher sind als solche, die Nachthemd oder Pyjama tragen. Allerdings stellt sich hier die berühmte Huhn-oder-Ei-Frage: Vielleicht schlafen glückliche Paare einfach häufiger nackt als unglückliche."
            7 -> content = "Die Wissenschaft hat bis heute keine schlüssige Antwort auf die Frage, weshalb wir gähnen. Früher hiess es, das Gehirn brauche sozusagen notfallmässig Sauerstoff, doch für diese These fehlen schlagkräftige Belege. Heute geht man davon aus, dass Gähnen ein soziales Signal ist, das konflikthemmend wirkt. Das würde auch erklären, weshalb es «ansteckend» ist – und zwar nicht nur beim Menschen, sondern auch bei Tieren. "
            8 -> content = "Meist stehen Schlafwandler im ersten Drittel des Nachtschlafs auf, für einige Minuten bis zu 30 Minuten. Am nächsten Morgen erinnern sie sich an nichts. Oft sind Schlafwandler Kinder zwischen sechs und zwölf Jahren. Bei den meisten normalisiert sich der Schlaf während der Pubertät. Schlafwandler bewegen sich immer mit offenen Augen auf die stärkste vorhandene Lichtquelle zu – früher war das der helle Mond, wie alte Darstellungen von Menschen in Nachthemden auf Hausdächern zeigen. Den Grund für die Suche nach Licht sehen Forscher darin, dass der Schlafwandler instinktiv das Bedürfnis hat, sich zu orientieren."
            9 -> content = "Jede und jeder Dritte ist von nächtlichem Zähneknirschen betroffen. Die Ursachen sind noch immer nicht schlüssig erforscht. Es wird jedoch vermutet, dass psychischer Stress eine wichtige Rolle spielt."
            else -> { // Note the block
                content = "doesnt exist"
            }
        }
        return content
    }

}

