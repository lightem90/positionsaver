create database fermate



create table operatore (idimei VARCHAR(15) PRIMARY KEY, anagrafica TEXT);

create table fermate (idaut INT AUTO_INCREMENT PRIMARY KEY, idimei VARCHAR(15), nomeFermata TEXT, indirizzo TEXT, descrizione TEXT, data DATETIME);

SELECT idimei, data FROM fermate WHERE idimei= 457619435671842 ORDER BY data DESC LIMIT 1;
				
				
insert into fermate(idimei, nomeFermata, indirizzo, descrizione, data) values (354692041143955, 'farmacia', 'via achilli', 'seconda fermata', '2012-05-16 12:44:35')

select nomeFermata, indirizzo from fermate join operatore on fermate.idimei = operatore.idimei where fermate.idimei = 457619435671842;

		String insertQuery = "INSERT INTO fermate (idimei, nomeFermata, indirizzo, descrizione, data) VALUES("
				+ idimei
				+ ", '"
				+ nomeFermata
				+ "', '"
				+ indirizzo
				+ "', '"
				+ descrizione + "', '" + data + "')";
		String datasyncQuery = "SELECT idimei, data FROM fermate WHERE idimei="
				+ idimei + " ORDER BY data DESC LIMIT 1";
				
				