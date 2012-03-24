<h1>LEDDriver v0.1</h1>
<ol>
	<h2><li>Instaling rxtx </h2>
		Get the <i>rxtx 2.2pre2 binary</i> at <a href="http://rxtx.qbang.org/wiki/index.php/Download" target="_blank"> http://rxtx.qbang.org/wiki/index.php/Download</a> and follow the instructions provided at <a href="http://rxtx.qbang.org/wiki/index.php/Installation" target="_blank">/Installation</a> (for windows see <a href ="http://rxtx.qbang.org/wiki/index.php/Installation_for_Windows" target="_blank">/Installation_for_Windows</a>).</p>
NOTE: <i>rxtxParallel.dll</i> might not be provided for some installation. But it's not need anyway.
	</li>
	<h2><li>Start LEDDriver.jar</h2>
		You can do so, either by using the command line, navigating to the folder, in which the jar file is stored, and enter <i>javaw -jar LEDDriver.jar</i> or starting the start.bat file, which does essentially the same. (careful: java is cASE sENSITIVE)
	</li>
	<h2><li>At runtime</h2>
		By default the <i>automode</i> is active. As the commandline interface is somehow not working for jar files, there is no way to change mode for now.
	</li>
	<h2><li>Stopping</h2>
		Close the panel showing the color. The process will terminate properely.
	</li>
	<h2><li>Known Issues</h2>
		<ul>
			<li> The COM port used, is the first one found. Deactivating the others (with lower number than the wanted) will solve this problem. For now there is no way to choose manually.
			</li>
		</ul>
	</li>
	<
</ol>