package com.perficient.util;

import java.io.File;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;


/**
 * SOAP Client Implementation using SAAJ Api.
 */
public class SoapApiUtil
{
	
	public SOAPMessage soapResponse;
	public SOAPMessage soapMessage; 
	
    /**
     * Method used to create the SOAP Request
     */
    public SOAPMessage createSOAPRequest(String requestName, String nameSpace,String nameSpaceValue,  String fieldName, String input)
    {
       MessageFactory messageFactory;
       try {
		    messageFactory = MessageFactory.newInstance();
			soapMessage = messageFactory.createMessage();
			
            SOAPPart soapPart = soapMessage.getSOAPPart();
            MimeHeaders headers = soapMessage.getMimeHeaders();
    	    headers.addHeader("SOAPAction", nameSpaceValue + requestName);
           
            /*
            Construct SOAP Request Message:
           	<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:web="http://www.webservicex.net/">
					<soapenv:Header/>
					<soapenv:Body>
  					<web:GetSupplierByCity>
     			<!--Optional:-->
     					<web:City>San Francisco</web:City>
  					</web:GetSupplierByCity>
					</soapenv:Body>
			</soapenv:Envelope>
             */

            // SOAP Envelope
            SOAPEnvelope envelope = soapPart.getEnvelope();
            envelope.addNamespaceDeclaration(nameSpace, nameSpaceValue);

            // SOAP Body
            SOAPBody soapBody = envelope.getBody();
            SOAPElement soapBodyElem = soapBody.addChildElement(requestName, nameSpace);
            SOAPElement soapBodyElem1 = soapBodyElem.addChildElement(fieldName, nameSpace);
            soapBodyElem1.addTextNode(input);

            soapMessage.saveChanges();

            // Check the input
            System.out.println("Request SOAP Message = ");
            System.out.println(soapMessage.toString());
            System.out.println();
           
            } catch (SOAPException e) {
				e.printStackTrace();
			}
            return soapMessage;
    }
    
    
	/**
	 * This method connects to wsdl and gets the response.
	 */
	public SOAPMessage connectToWsdl(SOAPMessage request, String wsdl)
	{
		try
		{
			// Create SOAP Connection
			 SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			 SOAPConnection soapConnection = soapConnectionFactory.createConnection();
			 soapResponse = soapConnection.call(request, wsdl);
		}
		catch (Exception e)
		{
			System.err.println("Error occurred while sending SOAP Request to Server");
			e.printStackTrace();
		}
		return soapResponse;
	} 	
    

        /**
         * Method used to print the SOAP Response and save it in .xml format in xmlfiles folder.
         * @param soapResponse
         * @throws Exception
         */
        public void printSOAPResponse(SOAPMessage soapResponse) 
        {
        	TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer;
			try {
				transformer = transformerFactory.newTransformer();
			
			Source sourceContent;
				sourceContent = soapResponse.getSOAPPart().getContent();
				
			System.out.println("\nResponse SOAP Message = ");
			StreamResult result = new StreamResult(System.out);
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			StreamResult file = new StreamResult(new File(System.getProperty("user.dir")+"\\testdata\\xmlfiles\\XMLFile_"+ComplexReportFactory.getDatetime()+".xml"));
			transformer.transform(sourceContent, result);
			transformer.transform(sourceContent, file);
			
            System.out.println("DONE");
			
			} catch (TransformerConfigurationException e) {
				e.printStackTrace();
			} catch (SOAPException e) {
				e.printStackTrace();
			} catch (TransformerException e) {
				e.printStackTrace();
			}
        }
        
}