/*
	@author Alizea LEBARON
	@version 1.1.0 - 12/12/2023 
	@date 06/12/2023 
	@description Script de creation de la base de donnees
*/

--FIXME: NE FONCTIONNE PAS A CAUSE DES ID DES MODULEIUT (code -> idModuleIUT)

UPDATE Annee SET Actuelle = false;

INSERT INTO Annee VALUES
('AnneeDemo', true);

INSERT INTO Semestre VALUES 
(1,8,4,100,13), 
(2,6,3,75,12), 
(3,4,2,50,13), 
(4,4,2,49,15), 
(5,4,2,48,11), 
(6,3,2,40,15); 

INSERT INTO Contrat ( nom, nbHeuresService, maxHeuresService, coefTP ) VALUES 
('Enseignant Chercheur',192,364,'1.0'), 
('Enseignant',384,576,'1.0'), 
('Contractuel',384,576,'0.66'), 
('Enseignant vacataire',0,187,'1.0'), 
('Agent temporaire',0,0,'1.0'); 

INSERT INTO Heure ( nom, coefEQTD ) VALUES 
('CM','1.5'), 
('TD','1.0'), 
('TP','1.0'), 
('REH','1.0'), 
('SAE','1.0'), 
('HP','1.0'), 
('Tut','1.0'); 

INSERT INTO ModuleIUT VALUES 
('R1.01','Initiation au developpement','init_dev','Ressource',true,1,304.0,184.0), 
('R1.02','Developpement interfaces Web','Dev_Web','Ressource',false,1,0.0,0.0), 
('R1.03','Introduction Architecture','intro_archi','Ressource',false,1,400.0,0.0), 
('R1.11','Bases de la communication','comm','Ressource',false,1,200.0,40.0), 
('S1.04','Creation  base de donnees','BADO','SAE',false,1,20.0,25.0), 
('R1.12','Projet Professionnel et Personnel','PPP','PPP',true,1,45.0,45.0), 
('R2.03','Qualite de developpement','quali_dev','Ressource',false,2,0.0,0.0), 
('S2.03','Installation de services reseau','serv_res','SAE',false,2,15.0,0.0), 
('S3.01','Developpement application','dev_app','SAE',false,3,140.0,0.0), 
('R4.01','Architecture logicielle','archi_log','Ressource',false,4,71.0,0.0), 
('R4.08','Virtualisation','virtualisation','Ressource',false,4,0.0,0.0), 
('S4.01','Developpement application','dev_app','SAE',false,4,55.0,0.0), 
('S4.ST','Stages','stage','Stage',false,4,15.0,0.0), 
('R5.08','Qualite de developpement','quali_dev','Ressource',false,5,0.0,0.0), 
('S5.01','Developpement avance','dev_avance','SAE',false,5,0.0,0.0), 
('R6.ST','Stage','stage','Stage',false,6,8.0,0.0), 
('S6.01','evolution deune application existante ','evo_app','SAE',false,6,0.0,0.0), 
('R3.08','Probabilites','proba','Ressource',true,3,40.0,40.0), 
('R2.11','Droit','droit','Ressource',false,2,90.0,30.0), 
('R4.10','Complement web','comp_web','Ressource',false,4,100.0,250.0), 
('R5.03','Politique de communication','comm','Ressource',false,5,210.0,80.0), 
('R6.01','Initiation entrepreneuriat','init_ent','Ressource',true,6,4.0,4.0); 

INSERT INTO Intervenant ( nom, prenom, nbHeuresService, maxHeuresService, idContrat ) VALUES 
('Goscinny','Rene',192,364,1), 
('Lovelace','Ada',384,576,2), 
('Lovecraft','Howard',20,187,4), 
('Fontaine','Jean',384,576,3), 
('Balzac','Honore',0,96,5); 

INSERT INTO Intervient VALUES 
(1,1,'R1.01',3,1,4.0,'1 CM / semaine'), 
(2,2,'R1.01',3,2,30.0,'...'), 
(3,2,'R1.01',3,2,30.0,'...'), 
(1,3,'R1.01',5,4,120.0,'...'), 
(2,3,'R1.01',5,4,0.0,'...'), 
(5,2,'R1.11',10,2,40.0,'...'), 
(2,5,'S1.04',1,15,15.0,'...'), 
(3,7,'S1.04',1,5,5.0,'...'), 
(4,7,'S1.04',1,5,5.0,'...'), 
(1,2,'R1.12',1,24,24.0,'...'), 
(2,6,'R1.12',1,20,20.0,'Revoir'), 
(5,1,'R1.12',1,1,1.0,'...'), 
(2,2,'R3.08',10,1,20.0,'...'), 
(3,2,'R3.08',10,1,20.0,'...'), 
(5,2,'R2.11',10,1,30.0,'...'), 
(4,2,'R4.10',10,5,250.0,'...'), 
(3,3,'R5.03',10,2,80.0,'...'), 
(3,2,'R6.01',1,2,4.0,'...'); 

INSERT INTO Horaire VALUES 
(1,'R1.01',3,1,3), 
(2,'R1.01',15,5,3), 
(3,'R1.01',30,6,5), 
(6,'R1.01',0,0,1), 
(1,'R1.02',0,0,0), 
(2,'R1.02',0,0,0), 
(3,'R1.02',0,0,0), 
(6,'R1.02',0,0,1), 
(1,'R1.03',0,0,0), 
(2,'R1.03',50,0,0), 
(3,'R1.03',25,0,0), 
(6,'R1.03',0,0,1), 
(1,'R1.11',0,0,0), 
(2,'R1.11',20,2,10), 
(3,'R1.11',15,3,5), 
(6,'R1.11',0,0,1), 
(5,'S1.04',15,15,1), 
(7,'S1.04',5,5,1), 
(1,'R1.12',1,1,1), 
(3,'R1.12',0,0,1), 
(2,'R1.12',6,6,1), 
(6,'R1.12',5,5,1), 
(1,'R2.03',0,0,0), 
(2,'R2.03',0,0,0), 
(3,'R2.03',0,0,0), 
(6,'R2.03',0,0,1), 
(5,'S2.03',15,15,1), 
(7,'S2.03',0,0,1), 
(5,'S3.01',140,0,1), 
(7,'S3.01',0,0,1), 
(1,'R4.01',2,2,1), 
(2,'R4.01',6,2,3), 
(3,'R4.01',14,2,7), 
(6,'R4.01',0,0,1), 
(1,'R4.08',0,0,0), 
(2,'R4.08',0,0,0), 
(3,'R4.08',0,0,0), 
(6,'R4.08',0,0,1), 
(5,'S4.01',40,40,1), 
(7,'S4.01',15,15,1), 
(4,'S4.ST',5,5,1), 
(7,'S4.ST',10,10,1), 
(1,'R5.08',0,0,0), 
(2,'R5.08',0,0,0), 
(3,'R5.08',0,0,0), 
(6,'R5.08',0,0,1), 
(5,'S5.01',0,0,1), 
(7,'S5.01',0,0,1), 
(4,'R6.ST',5,5,1), 
(7,'R6.ST',3,3,1), 
(5,'S6.01',0,0,1), 
(7,'S6.01',0,0,1), 
(1,'R3.08',0,0,0), 
(2,'R3.08',20,2,10), 
(3,'R3.08',0,0,0), 
(6,'R3.08',0,0,1), 
(1,'R2.11',0,0,0), 
(2,'R2.11',30,3,10), 
(3,'R2.11',0,0,0), 
(6,'R2.11',0,0,1), 
(1,'R4.10',0,0,0), 
(2,'R4.10',50,5,10), 
(3,'R4.10',0,0,0), 
(6,'R4.10',0,0,1), 
(1,'R5.03',0,0,0), 
(2,'R5.03',25,5,5), 
(3,'R5.03',40,4,10), 
(6,'R5.03',0,0,1), 
(1,'R6.01',0,0,0), 
(2,'R6.01',2,2,1), 
(3,'R6.01',0,0,0), 
(6,'R6.01',0,0,1); 

