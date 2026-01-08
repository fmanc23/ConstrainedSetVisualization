Constrained Set Visualization 
Un algoritmo avanzato per la visualizzazione di insiemi di punti con posizioni vincolate, sviluppato come progetto di tesi presso l'Università degli Studi di Perugia.

📋 Descrizione
Questo progetto affronta il problema della Constrained Set Visualization: dato un insieme di punti nel piano, ciascuno appartenente a uno o più insiemi, l'obiettivo è visualizzare i diversi gruppi connettendo con curve o racchiudendo in regioni chiuse tutti i punti di ciascun insieme, mantenendo la visualizzazione chiara e leggibile.
Caratteristiche Principali

Visualizzazione planare con minimo numero di incroci
Book embedding topologico su due pagine
Ottimizzazione automatica del layout per ridurre complessità visiva
Interfaccia interattiva con controlli di zoom e filtri per insieme
Supporto multi-insieme - ogni punto può appartenere a più gruppi

🎯 Casi d'Uso

Mappe geografiche: visualizzazione di ristoranti, hotel e punti d'interesse raggruppati per categoria
Analisi di reti sociali: rappresentazione di comunità e loro interazioni
Linguistica computazionale: identificazione di relazioni tra parole correlate
Data analysis: esplorazione visiva di dataset con proprietà multiple

🏗️ Architettura

Il sistema si compone di una pipeline algoritmica in due fasi:
1. Algoritmo Linear Layout

Ordinamento delle coordinate dei punti
Creazione della struttura grafica astratta
Inserimento intelligente degli archi per minimizzare incroci
Ottimizzazione del layout risultante

2. Algoritmo Costruzione Geometrica

Assegnazione coordinate ai nodi (reali e fittizi)
Creazione liste di adiacenza
Calcolo pendenze e angoli (bends)
Ottimizzazione dell'area occupata
Point-set embedding finale

Complessità: O(n³ · m³) nel caso peggiore, dove n = numero nodi, m = numero colori/insiemi

🛠️ Tecnologie

Java con librerie JFC/Swing
Supporto multi-piattaforma (Windows, Linux, macOS)
Algoritmi di graph drawing e point-set embedding
Tecniche di visualizzazione ispirate a Kelp Diagrams e LineSets

📦 Struttura del Progetto

src/
├── algorithm/          # Pipeline algoritmica principale
│   ├── BookEmbedding  # Creazione linear layout
│   ├── Drawing        # Costruzione geometrica
│   ├── Edge, Node     # Strutture dati base
│   └── GraphicEdge, GraphicNode, Bend
├── view/              # Interfaccia grafica
│   ├── MainGUI        # Rendering e interazioni
│   └── GeneralGUI     # Gestione finestra
└── utilities/         # Funzioni di supporto
    ├── ReadTextFile, WriteTextFile
    └── Assets, AbsolutePath

🚀 Come Usare

Prerequisiti

Java JDK 8 o superiore
IDE Java (Eclipse, IntelliJ IDEA, NetBeans)

Esecuzione
bash# Compila ed esegui
java Main

# Per esperimenti batch (senza GUI)
java Experiment
Controlli Interfaccia

Zoom In/Out: pulsanti in basso a destra
Reset: ripristina zoom e posizione
Drag mouse: sposta la visualizzazione
Checkbox colori: mostra/nascondi insiemi specifici

📊 Risultati Sperimentali

I test condotti hanno dimostrato:

✅ Planarità elevata anche con molti insiemi (fino a 10 colori)
✅ Numero ridotto di bend rispetto al numero di archi
✅ Minimizzazione degli incroci grazie all'ottimizzazione del layout
⚠️ Prestazioni: testato fino a 4000 nodi con 10 colori

Metriche Valutate

Tempo di esecuzione
Area occupata vs area minima
Numero di bend creati
Rapporto bend/archi
Numero di incroci

🎓 Contesto Accademico

Tesi di Laurea Triennale in Ingegneria Informatica ed Elettronica
Università degli Studi di Perugia (UNIPG)
Anno Accademico: 2022-2023
Relatore: Prof. Emilio Di Giacomo
Votazione: 97/110

🔮 Sviluppi Futuri

 Ottimizzazione algoritmica per migliorare l'efficienza
 Pre-processing per ordinamento ottimale dei punti
 Algoritmo avanzato per taglio archi con strutture ad albero
 Implementazione curve di Bézier per visualizzazione più compatta
 Supporto per dataset di dimensioni maggiori

📚 Riferimenti

Il progetto si basa su tecniche avanzate di visualizzazione come:

Kelp Diagrams e KelpFusion
LineSets
Book Embedding Theory
Point-Set Embedding Algorithms

📄 Licenza

Progetto accademico - Università degli Studi di Perugia
👤 Autore
Francesco Mancinelli
📧 f.mancinelli23@gmail.com
🔗 GitHub
