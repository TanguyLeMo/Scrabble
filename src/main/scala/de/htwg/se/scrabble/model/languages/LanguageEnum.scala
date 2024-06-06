package de.htwg.se.scrabble.model.languages

enum LanguageEnum {
  case ENGLISH, GERMAN, FRENCH, ITALIAN
}
object LanguageEnum {
  def toLanguage(language: String): LanguageEnum = language.toLowerCase match {
    case "german" | "deutsch" => GERMAN
    case "french" | "francais" | "français" => FRENCH
    case "italian" | "italiano" => ITALIAN
    case _ => ENGLISH
  }
}