package main.java.common;

public enum HttpMethod {
  /**
   * The GET method is used to retrieve information from the given server using a given URI.
   * Requests using GET should only retrieve data and should have no other effect on the data.
   */
  GET,
  /**
   * Same as GET, but it transfers the status line and the header section only.
   */
  HEAD,
  /**
   * A POST request is used to send data to the server, for example, customer information, file
   * upload, etc. using HTML forms.
   */
  POST,
  /**
   * Replaces all the current representations of the target resource with the uploaded content.
   */
  PUT,
  /**
   * Removes all the current representations of the target resource given by URI.
   */
  DELETE,
  /**
   * Establishes a tunnel to the server identified by a given URI.
   */
  CONNECT,
  /**
   * Describe the communication options for the target resource.
   */
  OPTIONS,
  /**
   * Performs a message loop back test along with the path to the target resource.
   */
  TRACE;
}
