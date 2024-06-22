package de.htwg.se.scrabble.model.languageComponent.languages

import de.htwg.se.scrabble.model.languageComponent.LanguageStrategy

class FrenchStrategy extends LanguageStrategy{
  override val languageSetting: String = "Langue: "
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
  override def nameAlreadyTaken: String = "Le nom est déjà pris"
  override def enterNumberofPlayers: String = "Entrez le nombre de joueurs"
  override def invalidNumber: String = "Entrée invalide, veuillez entrer un nombre valide"
  override def enterPlayernames: String = "Entrez les noms du premier / prochain joueur "
  override def nameCantBeEmpty: String = "Le nom ne peut pas être vide"
  override def currentPlayer: String = "Joueur actuel: "
  override def noteEnoughStones: String = "Pas assez de pierres"
  override def leaderBoard: String = "Classement"
}