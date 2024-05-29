package de.htwg.se.scrabble.aview.languages

class FrenchStrategy extends LanguageStrategy{
  override def languageSetting: String = "Langue: "
  override def requestNewWord: String = "Entrez un nouveau mot"
  override def wordAlreadyAddedToDictionary: String = "Le mot a déjà été ajouté au dictionnaire"
  override def wordAddedToDictionary: String = "Le mot a été ajouté au dictionnaire"
  override def invalidcoordinates: String = "Coordonnées invalides"
  override def invalidInput: String = "Entrée invalide"
  override def notInDictionary: String = " n'est pas dans le dictionnaire"
  override def wordNotInDictionary: String = " n'est pas dans le dictionnaire"
  override def enterWordforDictionary: String = "Entrez un mot pour le dictionnaire"
  override def stop: String = "stop"
  override def enterWord: String = "Entrez votre mot: et Direction (H|V) exemple: monMot A 0 H"
  override def NoCorrectDirection: String = "Aucune direction correcte"
  override def wordDoesntFit: String = "Le mot ne convient pas"
  override def exit: String = "sortir"
}