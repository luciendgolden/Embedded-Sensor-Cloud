package at.technikum.swe.common;

public enum ContentTypes {
  /**
   * Type application
   */
  APPLICATION_EDI_X12("application/EDI-X12"),
  APPLICATION_EDIFACT("application/EDIFACT"),
  APPLICATION_JAVASCRIPT("application/javascript"),
  APPLICATION_OCTET_STREAM("application/octet-stream"),
  APPLICATION_OGG("application/ogg"),
  APPLICATION_PDF("application/pdf"),
  APPLICATION_X_SHOWWAVE_FLASH("application/x-shockwave-flash"),
  APPLICATION_JSON("application/json"),
  APPLICATION_LD_JSON("application/ld+json"),
  APPLICATION_XML("application/xml"),
  APPLICATION_ZIP("application/zip"),

  /**
   * Type audio
   */
  AUDIO_MPEG("audio/mpeg"),
  AUDIO_X_MS_WMA("audio/x-ms-wma"),
  AUDIO_VND_RN_REALAUDIO("audio/vnd.rn-realaudio"),
  AUDIO_X_WAY("audio/x-wav"),

  /**
   * Type image
   */
  IMAGE_GIF("image/gif"),
  IMAGE_JPEG("image/jpeg"),
  IMAGE_PNG("image/png"),
  IMAGE_TIFF("image/tiff"),
  IMAGE_X_ICON("image/x-icon"),
  IMAGE_VND_DJVU("image/vnd.djvu"),
  IMAGE_SVG_XML("image/svg+xml"),

  /**
   * Type multipart
   */
  MULTIPART_MIXED("multipart/mixed"),
  MULTIPART_ALTERNATIVE("multipart/alternative"),
  MULTIPART_RELATED("multipart/related"),

  /**
   * Type text
   */
  TEXT_CSS("text/css"),
  TEXT_CSV("text/csv"),
  TEXT_HTML("text/html"),
  TEXT_JAVASCRIPT("text/javascript"),
  TEXT_PLAIN_TXT("text/plain"),
  TEXT_XML("text/xml");


  private final String value;

  ContentTypes(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
