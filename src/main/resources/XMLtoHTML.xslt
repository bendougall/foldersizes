<?xml version="1.0"?>

<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
  <html>
  <head>
   <style>
      html,body 
      {
         font-family: Verdana,sans-serif;
         font-size: 12px;
         line-height: 1.5;
         padding: 0px;
         margin: 0px;
      }
      h1,h2,h3,h4,h5,h6 
      {
         font-family: "Segoe UI",Arial,sans-serif;         
         font-weight: bold;
         border-bottom: 1px solid black;
      }
      table
      {
         border-collapse: collapse;
      }
      table.results
      {
         font-family: "Segoe UI",Arial,sans-serif;
      }      
      table.results tr:nth-child(even) {
         background: #EBEBEB;
      }
      td.results
      {
         margin-top: 2px;
         margin-bottom: 2px;
         margin-left: 5px;
         margin-right: 5px;
         font-size: 12px;
      }
      thead.results
      {
         border-bottom: 4px solid #333333;
      }
      table.greyGridTable thead th 
      {
		  font-size: 15px;
		  font-weight: bold;
		  color: #333333;
		  text-align: center;
      }
      table.results thead th:first-child 
      {
         border-left: none;
      }
      table.results th
      {
         font-family: "Segoe UI",Arial,sans-serif;
         font-size: 15px;
         font-weight: bold;
         color: #333333;
         padding: 3px 4px;
         background-color: #BBBBBB;
      }
      td.headerCell
      {
         vertical-align: center;
         background-color: black;
         color: white;
         font-size: 22px;
         font-weight: bold;
         padding-top: 12px;
         padding-bottom: 12px;
         padding-left: 6px;
      }
      td.summaryCell
      {
         font-family: "Segoe UI",Arial,sans-serif;
         font-size: 16px;
         width: 175px;
         vertical-align: top;
         background-color: black;
         color: white;
         padding-left: 6px;
         padding-right: 6px;
      }
      p.summaryLabel
      {
         font-family: "Segoe UI",Arial,sans-serif;
         font-weight: bold;
         font-size: 16px;
      }
      p.summaryValue
      {
         text-align: right;
      }
      .summary
      {
         font-weight: bold;
         padding-left: 10px;
      }
      td.numberCell
      {
         text-align: right;
      }
   </style>
   
  </head>
  <body>
    <table width="100%">
    <tr>
      <td class="headerCell" colspan="2">
         BJAD Folder Sizes Report of <i><xsl:value-of select="exportData/directoryForResult/path"/></i>
      </td>
    </tr>
    <tr>
      <td class="summaryCell">
        <h3>Total File Size</h3>
        <p class="summaryValue"><xsl:value-of select="exportData/directoryForResult/size"/></p>
        <h3>Total File Count</h3>
        <p class="summaryValue"><xsl:value-of select="exportData/directoryForResult/totalFileCount"/></p>
        <h3>Total Directory Count</h3>
        <p class="summaryValue"><xsl:value-of select="exportData/directoryForResult/totalDirCount"/></p>
        <br />
        <br />
        <h3>Date of findings</h3>
        <p class="summaryValue"><xsl:value-of select="exportData/dateOfReport"/></p>
      </td>
      <td style="padding-left: 10px;">	        
	    <h3>The Top <xsl:value-of select="count(exportData/largestDirectories)" /> Largest Directories Found</h3>
	    <table class="results" border="1">
	      <thead>
		      <tr>
		        <th>Path</th>
		        <th>Total Size</th>
		        <th>File Count</th>
		        <th>Directory Count</th>
		      </tr>
	      </thead>
	      <xsl:for-each select="exportData/largestDirectories">
	        <tr>
	          <td><xsl:value-of select="path"/></td>
	          <td class="numberCell"><xsl:value-of select="size"/></td>
	          <td class="numberCell"><xsl:value-of select="totalFileCount"/></td>
	          <td class="numberCell"><xsl:value-of select="totalDirCount"/></td>
	        </tr>
	      </xsl:for-each>
	    </table>
	    <h3>The Top <xsl:value-of select="count(exportData/largestFiles)" /> Largest Files Found</h3>
	    <table class="results" border="1">
	      <thead>
		      <tr>
		        <th>Path</th>
		        <th>File Size</th>
		        <th>Modified Date</th>
		      </tr>
	      </thead>
	      <xsl:for-each select="exportData/largestFiles">
	        <tr>
	          <td><xsl:value-of select="path"/></td>
	          <td class="numberCell"><xsl:value-of select="sizeStr"/></td>
	          <td class="numberCell"><xsl:value-of select="lastModifiedDate"/></td>
	        </tr>
	      </xsl:for-each>
	    </table>
	    <h3>The Top <xsl:value-of select="count(exportData/largestExtensions)" />  Largest File Extensions Found</h3>
	    <table class="results" border="1">
	      <thead>
		      <tr>
		        <th>Extension</th>
		        <th>Total Size</th>
		        <th>File Count</th>
		      </tr>
	      </thead>
	      <xsl:for-each select="exportData/largestExtensions">
	        <tr>
	          <td><xsl:value-of select="extension"/></td>
	          <td class="numberCell"><xsl:value-of select="size"/></td>
	          <td class="numberCell"><xsl:value-of select="fileCount"/></td>
	        </tr>
	      </xsl:for-each>
	    </table>
	    <h3>Directory Information (sorted by total size)</h3>
       <table class="results" border="1">
         <thead>
            <tr>
              <th>Path</th>
              <th>Total Size</th>
              <th>File Count</th>
              <th>Directory Count</th>
            </tr>
         </thead>
         <xsl:for-each select="exportData/directories">
           <tr>
             <td><xsl:value-of select="path"/></td>
             <td class="numberCell"><xsl:value-of select="size"/></td>
             <td class="numberCell"><xsl:value-of select="totalFileCount"/></td>
             <td class="numberCell"><xsl:value-of select="totalDirCount"/></td>
           </tr>
         </xsl:for-each>
       </table>
	    </td>
	    </tr>
    </table>
  </body>
  </html>
</xsl:template>

</xsl:stylesheet>