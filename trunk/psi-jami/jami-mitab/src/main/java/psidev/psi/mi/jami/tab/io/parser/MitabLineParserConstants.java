/* Generated By:JavaCC: Do not edit this line. MitabLineParserConstants.java */
package psidev.psi.mi.jami.tab.io.parser;


/**
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 */
public interface MitabLineParserConstants {

  /** End of File. */
  int EOF = 0;
  /** RegularExpression Id. */
  int UNRESERVED_STRING = 3;
  /** RegularExpression Id. */
  int QUOTED_STRING = 4;
  /** RegularExpression Id. */
  int EMPTY_COLUMN = 5;
  /** RegularExpression Id. */
  int PUB_DATE = 6;
  /** RegularExpression Id. */
  int TAXID = 7;
  /** RegularExpression Id. */
  int NEGATIVE = 8;
  /** RegularExpression Id. */
  int POSITION = 9;
  /** RegularExpression Id. */
  int STOICHIOMETRY = 10;
  /** RegularExpression Id. */
  int COMMENT = 11;
  /** RegularExpression Id. */
  int FIELD_SEPARATOR = 12;
  /** RegularExpression Id. */
  int COLUMN_SEPARATOR = 13;
  /** RegularExpression Id. */
  int LINE_SEPARATOR = 14;
  /** RegularExpression Id. */
  int RANGE_SEPARATOR = 15;

  /** Lexical state. */
  int DEFAULT = 0;

  /** Literal token values. */
  String[] tokenImage = {
    "<EOF>",
    "\"\\r\"",
    "\"\\f\"",
    "<UNRESERVED_STRING>",
    "<QUOTED_STRING>",
    "\"-\"",
    "<PUB_DATE>",
    "\"taxid\"",
    "<NEGATIVE>",
    "<POSITION>",
    "<STOICHIOMETRY>",
    "<COMMENT>",
    "\"|\"",
    "\"\\t\"",
    "\"\\n\"",
    "\",\"",
    "\":\"",
    "\"(\"",
    "\")\"",
    "\" \"",
  };

}
