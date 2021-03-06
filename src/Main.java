
public class Main
{
	public static void main(String[] args)
	{
		// * Die folgenden Werte beziehen sich immer auf ein Jahr, in dem sich der Depotwert entwickelt!
		// * Es werden keine Steuern per Steuererklärung zurückerstattet.
		// * Es wird jedes Jahr ein Gewinn realisiert (s. unten die "jahresRendite")
		// * Es wird angenommen, dass der Gewinn jedes Jahr ausgezahlt wird und direkt Abzug etwaiger Steuern reinvestiert wird.
		// * Dieses Szenario betrifft eher kurz- bis mittelfristige Investoren (Anlagezeitraum immer < Jahr bzw. bis ein Jahr).
		
		// Anlagestrategie
		int anlageZeitraum = 20; // Jahre
		double kapitaleinsatz = 2500; // €
		double jahresRendite = 8; // %
		
		// Beruf (Nettolohn nach Abzug aller Steuern, Miete & Unterhaltungskosten gemeint)
		double vomNettoLohnAnlegen = 20; // %
		
		// Beruf (bitte Anlagezeitraum in drei "Phasen" einteilen, wo das Gehalt sich verändert).
		// Beispiel: 32 Jahre wird etwas angelegt. C.a. alle 10 bis 11 Jahre ändert sich also das Gehalt.
		// Daher bei erstesGehalt, zweitesGehalt und drittesGehalt bitte den entsprechenden Bruttolohn pro Monat schätzen / eingeben.
		// Eine geringfügige Beschäftigung (bis 450) wird steuerlich berücksichtigt!
		double erstesGehalt = 200;
		double zweitesGehalt = 0;
		double drittesGehalt = 0;
		
		// Außergewöhnliche Einkünfte, z.B. Geburtstag etc.
		double unversteuerteNebeneinkuenfte = 150; // 
		
		// Persönlicher Steuersatz (als Durchschnitt zu betrachten)
		double einkommenssteuersatz = 37; // %
		
		// Gesetzliche Steuersätze
		double kapitalertragssteuer = 25; // %
		double soli_zuschlag = 5.5; // %
		
		// "Beitragssteuern"
		double kirchensteuer = 8; // %
		
		// Gesetzliche Freibeträge
		double sparer_pauschbetrag = 801; //  (Single)
		double grundfreibetrag = 9744; //  (2021)
		
		// Steuerliche Situation
		boolean steuernWerdenGenerellGezahlt = true;
		boolean kirchensteuerPflichtig = false;
		
		boolean nvBescheinigug = true; // Liegt der Bank vom Finanzamt vor?
		boolean freistellauftrag = false; // Wurde gestellt beim Brokerage?
		
		
		
		
		
		
		
		
		
		
		// Folgende Werte sind nur für die interne Rechnung wichtig, daher nicht verändern / beachten!
		
		double aktuellesJahreseinkommen = 0; // Platzhalter, nicht beachten!
		double depotwert = 0; // wächst sich mit fortschreitenden Kapitalerträgen und Einzahlungen.
		float phaseZeitraum = anlageZeitraum / 3; // in Jahren
		int gezaehlteJahre = 0;
		
		// Fällige Kapitalertragssteuer ermitteln.
		if(kirchensteuerPflichtig)
		{
			kapitalertragssteuer = (kapitalertragssteuer/100 + soli_zuschlag/100 + kirchensteuer/100);
		}
		else
		{
			kapitalertragssteuer = (kapitalertragssteuer/100 + soli_zuschlag/100);
		}
		
		for(int jahre = 0; jahre < anlageZeitraum; jahre++)
		{
			if(jahre <= phaseZeitraum)
			{
				if(jahre == 0)
				{
					depotwert = kapitaleinsatz;
				}
				
				aktuellesJahreseinkommen = erstesGehalt*12;
				
				if(erstesGehalt > 450)
				{
					aktuellesJahreseinkommen *= (1-(einkommenssteuersatz/100));
				}
			}
			else if(jahre <= 2*phaseZeitraum)
			{
				aktuellesJahreseinkommen = zweitesGehalt*12;
				
				if(zweitesGehalt > 450)
				{
					aktuellesJahreseinkommen *= (1-(einkommenssteuersatz/100));
				}
			}
			else if(jahre <= 3*phaseZeitraum)
			{
				aktuellesJahreseinkommen = drittesGehalt*12;
				
				if(drittesGehalt > 450)
				{
					aktuellesJahreseinkommen *= (1-(einkommenssteuersatz/100));
				}
			}

			double bruttoGewinn = depotwert * (1+(jahresRendite/100)) - depotwert;

			double steuerfreierBetragInsgesamt = 0;
			
			// Sparer-Pauschbetrag von 801 als Single.
			if(freistellauftrag)
			{
				steuerfreierBetragInsgesamt += sparer_pauschbetrag;
			}
			
			// Grundfreibetrag, der von der Steuerlast zusätzlich neben dem Sparer-Pauschbetrag abgezogen werden kann.
			if(nvBescheinigug)
			{
				steuerfreierBetragInsgesamt += grundfreibetrag;
			}
			
			double nettoGewinn = bruttoGewinn;
			
			if(steuernWerdenGenerellGezahlt)
			{
				if(bruttoGewinn > steuerfreierBetragInsgesamt)
				{
					nettoGewinn -= ((bruttoGewinn - steuerfreierBetragInsgesamt) * kapitalertragssteuer);
				}
				else
				{
					nettoGewinn = bruttoGewinn;
				}
			}

			depotwert += nettoGewinn + (aktuellesJahreseinkommen + unversteuerteNebeneinkuenfte) * (vomNettoLohnAnlegen/100d);

			System.out.println("[Depotwert " + (jahre+1) + ". Jahr] " + (float) depotwert + "€");

			gezaehlteJahre++;
		}

		System.out.println("Wenn man anfangs " + (int) kapitaleinsatz + "€ investiert hat, sind die Wertpapiere nach " + gezaehlteJahre + " Jahren rund " + (int) depotwert + "€ wert.");
	}
}