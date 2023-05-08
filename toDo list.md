---
data: 07/05/2023
author: Alessandro M
description: toDo list di cose da aggiungere e appunti
---


#Cose da aggiungere che mancano:

0 - Change the serializable info of cell, byte adress should be swapped with the correct value of that information in memory.
    So when the SW create a new cell, the method in SW system must be changed to write the correct value of that memory adress.
    In the method in SW system for encrypting and decrypting data is based on the adress memory for safety, now when a cell is created the 
    byte's value must be :
    1 - swapped with a phisical data
    2 - when the user whant to decrypt a message, the decrypting method must convert the data in a reliable data, tath can be processed for decryption.

3 - Manca l'ordinamento della lista quando si aggiunge qualcosa, quando la si elimina e quando si sposta o modifica

4 - aggiungere un layer di sicurezza di livello avanzato per un certo tipo di password, particolarmente sensibili
        |
        |
        \-->> Implementare un'algoritmo di suddivisione del messaggio, riordinamento dei caratteri, cifratura a doppia chiave


5 - Ottimizzare il codice per un miglior funzionamento

##--- Idee future da implem,entare ---

Sostituire all'uso della lista un'albero binario


Miglior criptazione dei dati.
Quando i dati vendgono scritti su disco dovrebbero essere criptati a loro volta, e archiviati in una posizione specifica nella home dell'utente.



Sarebbe più interessante riconoscere il sistema operativo per differire le locazioni di salvataggio.

