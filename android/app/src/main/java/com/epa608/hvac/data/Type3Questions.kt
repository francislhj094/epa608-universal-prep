package com.epa608.hvac.data

import com.epa608.hvac.domain.EPASection
import com.epa608.hvac.domain.Question

object Type3Questions {
    val list: List<Question> = listOf(
        Question(
            id = "t3-1",
            section = EPASection.TYPE_III,
            questionText = "What is the primary function of a high-efficiency purge unit on a low-pressure centrifugal chiller?",
            options = listOf("To purge excess compressor lubricating oil", "To condense refrigerant vapor and vent non-condensable air and moisture that leaks into the chiller", "To release excess refrigerant when high condensing temperatures occur", "To scrub scale from the condenser water tubes"),
            correctIndex = 1,
            explanation = "Because low-pressure chillers operate in a vacuum, air and moisture leak in through gaskets. The purge unit condenses the refrigerant vapor and vents non-condensable gases to the atmosphere.",
            citation = "40 CFR § 82.156(b)",
            isA2L = false
        ),
        Question(
            id = "t3-2",
            section = EPASection.TYPE_III,
            questionText = "When leak testing a low-pressure centrifugal chiller using dry nitrogen, what is the maximum recommended pressure to avoid rupturing the safety relief disc?",
            options = listOf("5 psig", "10 psig", "15 psig", "25 psig"),
            correctIndex = 1,
            explanation = "Low-pressure chiller rupture discs are set to burst at 15 psig. To prevent accidental rupture during nitrogen leak testing, technicians pressurize to a maximum of 10 psig.",
            citation = "ASHRAE Standard 15 / Section 608",
            isA2L = false
        ),
        Question(
            id = "t3-3",
            section = EPASection.TYPE_III,
            questionText = "Under 40 CFR § 82.156 Table 1, what evacuation level is required prior to opening a low-pressure centrifugal chiller for a major repair using post-1993 recovery equipment?",
            options = listOf("0 psig", "15 inches of Hg vacuum", "25 inches of Hg vacuum", "25 mm Hg absolute"),
            correctIndex = 3,
            explanation = "40 CFR § 82.156 Table 1 mandates 25 mm Hg absolute for low-pressure appliances serviced with post-Nov 15, 1993 equipment. Pre-1993 equipment required 25 inches of Hg vacuum.",
            citation = "40 CFR § 82.156 Table 1",
            isA2L = false
        ),
        Question(
            id = "t3-4",
            section = EPASection.TYPE_III,
            questionText = "What precaution must be taken when recovering refrigerant from a low-pressure water chiller to prevent catastrophic tube freeze-up?",
            options = listOf("The building thermostat must be set to 80°F", "The chilled water and condenser water circulating pumps must remain operational during recovery", "The oil heater must be turned off immediately", "The technician must recover only liquid and vent all vapor"),
            correctIndex = 1,
            explanation = "Rapid evacuation and refrigerant boiling drop temperatures inside the heat exchanger. Chilled water circulating pumps must run continuously to keep water moving and prevent freezing and bursting the tubes.",
            citation = "ASHRAE Standard 15 / Type III Prep",
            isA2L = false
        ),
        Question(
            id = "t3-5",
            section = EPASection.TYPE_III,
            questionText = "At what temperature should the lubricating oil in a low-pressure centrifugal chiller be maintained by the crankcase heater to minimize refrigerant absorption into the oil?",
            options = listOf("70°F", "100°F", "130°F (approx. 54°C)", "212°F"),
            correctIndex = 2,
            explanation = "Low-pressure refrigerants dissolve rapidly into compressor oil at lower temperatures. Keeping oil at 130°F drives out dissolved refrigerant, preventing foaming and loss of lubrication on startup.",
            citation = "ASHRAE Standard 15 / Type III",
            isA2L = false
        ),
        Question(
            id = "t3-6",
            section = EPASection.TYPE_III,
            questionText = "At what pressure does the safety rupture disc on a low-pressure centrifugal chiller discharge to prevent vessel explosion?",
            options = listOf("5 psig", "15 psig", "30 psig", "50 psig"),
            correctIndex = 1,
            explanation = "Low-pressure chillers are designed to operate in a vacuum and have thin evaporator shell walls. The standard rupture disc setting is strictly 15 psig (piped outdoors per ASHRAE 15).",
            citation = "ASHRAE Standard 15 / 40 CFR § 82.156",
            isA2L = false
        ),
        Question(
            id = "t3-7",
            section = EPASection.TYPE_III,
            questionText = "When recharging a low-pressure chiller (e.g. R-123) after a complete evacuation, why must refrigerant vapor be charged first before introducing liquid?",
            options = listOf("Liquid refrigerant will ruin the oil filter element", "Liquid refrigerant expanding in a deep vacuum flash boils, dropping temperature below 32°F and freezing water inside the chiller tubes", "Vapor refrigerant is heavier than liquid refrigerant in low-pressure systems", "EPA regulations strictly prohibit charging liquid refrigerant into any HVAC system"),
            correctIndex = 1,
            explanation = "Low-pressure liquid refrigerant boils at low temperatures under vacuum. Introducing liquid into a deep vacuum causes rapid flash boiling that drops temperatures below 32°F, freezing and bursting the water tubes.",
            citation = "40 CFR § 82.156(b)",
            isA2L = false
        ),
        Question(
            id = "t3-8",
            section = EPASection.TYPE_III,
            questionText = "To what minimum saturation temperature or pressure must a low-pressure chiller (such as an R-123 system) be vapor charged before liquid refrigerant can be safely introduced?",
            options = listOf("Until the saturation temperature is above 32°F (e.g. at least 36°F / approx. 16.9 inches Hg vacuum for R-123)", "Until pressure reaches 100 psig", "Until the purge unit starts pumping liquid", "Liquid can be added immediately at any vacuum level"),
            correctIndex = 0,
            explanation = "Vapor charging must continue until saturation temperature exceeds 36°F (above water's 32°F freezing point) before liquid can be introduced without risk of tube rupture.",
            citation = "Trane Centravac Service Guide",
            isA2L = false
        ),
        Question(
            id = "t3-9",
            section = EPASection.TYPE_III,
            questionText = "Where is the safety rupture disc physically located on a typical low-pressure centrifugal chiller?",
            options = listOf("On the cooling tower supply line", "On the evaporator shell, downstream of the rupture disc discharge pipe, venting outdoors", "Inside the oil pump housing", "Directly in the purge unit exhaust line"),
            correctIndex = 1,
            explanation = "The rupture disc is mounted directly on the low-pressure evaporator shell, which has the lowest pressure design rating, and is piped outdoors per safety codes.",
            citation = "ASHRAE Standard 15 § 9.4",
            isA2L = false
        ),
        Question(
            id = "t3-10",
            section = EPASection.TYPE_III,
            questionText = "Where does the discharge piping from a low-pressure chiller safety rupture disc legally have to terminate under ASHRAE Standard 15?",
            options = listOf("Directly inside the mechanical equipment room near the floor", "To the outdoors at an approved elevation and location at least 15 feet above grade and away from building air intakes", "Into the building storm sewer drain", "Inside the building stairwell"),
            correctIndex = 1,
            explanation = "ASHRAE 15 mandates that pressure relief and rupture disc piping must discharge outdoors into the atmosphere at a safe elevation away from windows and fresh air intakes.",
            citation = "ASHRAE Standard 15 § 9.7",
            isA2L = false
        ),
        Question(
            id = "t3-11",
            section = EPASection.TYPE_III,
            questionText = "What is the primary operational symptom of excessive air leakage into a low-pressure centrifugal chiller?",
            options = listOf("Low evaporator pressure and ice on the chill water barrel", "Frequent or continuous purge unit operation and elevated condenser head pressure", "The compressor motor speed drops by 50%", "The oil level in the sight glass drops to zero immediately"),
            correctIndex = 1,
            explanation = "Air cannot condense in the condenser, accumulating at the top and raising discharge pressure; the purge unit runs frequently to exhaust trapped non-condensables.",
            citation = "York Chiller Operation Manual",
            isA2L = false
        ),
        Question(
            id = "t3-12",
            section = EPASection.TYPE_III,
            questionText = "Where does the purge unit on a low-pressure chiller draw its sample vapor from?",
            options = listOf("From the bottom of the evaporator shell", "From the top of the condenser shell, where non-condensable gases naturally accumulate", "From the compressor oil sump", "From the chilled water return line"),
            correctIndex = 1,
            explanation = "Because non-condensable air and nitrogen are pushed by the compressor into the condenser and cannot liquefy, they collect at the highest point of the condenser shell.",
            citation = "Carrier Chiller Engineering Manual",
            isA2L = false
        ),
        Question(
            id = "t3-13",
            section = EPASection.TYPE_III,
            questionText = "What occurs to the refrigerant vapor that is condensed by a modern high-efficiency purge unit?",
            options = listOf("It is vented directly to the atmosphere", "It is automatically separated from condensed moisture and returned as liquid to the evaporator shell", "It is burned off with an electric heater", "It is pumped into the cooling tower"),
            correctIndex = 1,
            explanation = "Modern purge units use small condensing coils to liquefy refrigerant vapor and return it to the evaporator, while air and separated water are expelled.",
            citation = "40 CFR § 82.156(b)",
            isA2L = false
        ),
        Question(
            id = "t3-14",
            section = EPASection.TYPE_III,
            questionText = "What safety classification does HCFC-123 hold under ASHRAE Standard 34?",
            options = listOf("A1", "A2L", "B1 (higher toxicity, lower flammability)", "B3"),
            correctIndex = 2,
            explanation = "HCFC-123 is classified as Class B1: non-flammable under standard conditions, but possessing higher toxicity with an Occupational Exposure Limit (OEL) of 50 ppm.",
            citation = "ASHRAE Standard 34",
            isA2L = false
        ),
        Question(
            id = "t3-15",
            section = EPASection.TYPE_III,
            questionText = "Because HCFC-123 is an ASHRAE Class B1 refrigerant, what mandatory safety equipment is required in the machinery room under ASHRAE Standard 15?",
            options = listOf("An open wood-burning fireplace", "A continuous refrigerant vapor sensor that triggers audible and visual alarms and mechanical room exhaust ventilation at its 50 ppm exposure limit", "A chemical sprinkler deluge system", "Protective earmuffs for all visitors"),
            correctIndex = 1,
            explanation = "ASHRAE 15 mandates room leak detectors calibrated to activate emergency exhaust fans and exterior alarms when vapor concentrations reach the 50 ppm TLV-TWA threshold.",
            citation = "ASHRAE Standard 15 § 8.11",
            isA2L = false
        ),
        Question(
            id = "t3-16",
            section = EPASection.TYPE_III,
            questionText = "What is the primary replacement refrigerant for HCFC-123 in modern new low-pressure centrifugal chillers?",
            options = listOf("R-410A", "HFO-1233zd(E)", "R-22", "R-502"),
            correctIndex = 1,
            explanation = "HFO-1233zd(E) is a non-flammable (A1), ultra-low GWP (< 1) hydrofluoroolefin specifically engineered as a direct low-pressure replacement for HCFC-123.",
            citation = "EPA SNAP Approval Listing / ASHRAE 34",
            isA2L = false
        ),
        Question(
            id = "t3-17",
            section = EPASection.TYPE_III,
            questionText = "What is the safety classification of HFO-1233zd(E) under ASHRAE Standard 34?",
            options = listOf("A1 (lower toxicity, non-flammable)", "A2L", "B1", "B2L"),
            correctIndex = 0,
            explanation = "HFO-1233zd(E) achieves an ASHRAE Class A1 safety classification: non-flammable and lower toxicity (unlike toxic B1 HCFC-123).",
            citation = "ASHRAE Standard 34",
            isA2L = false
        ),
        Question(
            id = "t3-18",
            section = EPASection.TYPE_III,
            questionText = "How can a low-pressure centrifugal chiller be pressurized safely for leak detection without using nitrogen?",
            options = listOf("By filling the evaporator with motor oil", "By circulating controlled warm water (approx. 90°F to 100°F) through the chiller tubes or using specialized electric warming blankets to raise refrigerant saturation pressure", "By lighting an open flame under the evaporator barrel", "By running the compressor backward"),
            correctIndex = 1,
            explanation = "Warming chiller water tubes boils liquid refrigerant inside the shell, elevating internal vapor pressure up to 5 to 8 psig safely without exceeding the 15 psig rupture disc limit.",
            citation = "Trane Centravac Service Bulletins",
            isA2L = false
        ),
        Question(
            id = "t3-19",
            section = EPASection.TYPE_III,
            questionText = "Why should the water temperature in a low-pressure chiller NEVER exceed 130°F during warm water pressurization?",
            options = listOf("The paint on the shell will peel", "Vapor pressure can rise above 15 psig, causing the safety rupture disc to burst and vent the entire refrigerant charge", "The copper tubes will melt", "The oil will turn into gasoline"),
            correctIndex = 1,
            explanation = "Water temperatures above 130°F generate saturation pressures exceeding 15 psig for R-123/R-11, which will blow the thin rupture disc.",
            citation = "Carrier Chiller Service Guidelines",
            isA2L = false
        ),
        Question(
            id = "t3-20",
            section = EPASection.TYPE_III,
            questionText = "Under 40 CFR § 82.156 Table 1, what evacuation level was required for low-pressure appliances using recovery equipment manufactured BEFORE November 15, 1993?",
            options = listOf("0 psig", "10 inches of Hg vacuum", "25 inches of Hg vacuum", "25 mm Hg absolute"),
            correctIndex = 2,
            explanation = "Table 1 mandates 25 inches of mercury (Hg) vacuum for low-pressure systems serviced with pre-Nov 15, 1993 recovery equipment.",
            citation = "40 CFR § 82.156 Table 1",
            isA2L = false
        ),
        Question(
            id = "t3-21",
            section = EPASection.TYPE_III,
            questionText = "What is the relationship between 25 mm Hg absolute and inches of mercury vacuum at standard atmospheric pressure (29.92\" Hg)?",
            options = listOf("25 mm Hg absolute is approximately equivalent to 29 inches of Hg vacuum (approx. 28.9\" to 29.0\" Hg vacuum)", "25 mm Hg absolute is equal to 0 psig", "25 mm Hg absolute is 5 inches of vacuum", "They are completely unrelated measurements"),
            correctIndex = 0,
            explanation = "Standard atmospheric pressure is 760 mm Hg. An absolute pressure of 25 mm Hg leaves only 25/760th of an atmosphere, corresponding to ~28.93 inches of Hg vacuum.",
            citation = "Thermodynamic Conversion Standards",
            isA2L = false
        ),
        Question(
            id = "t3-22",
            section = EPASection.TYPE_III,
            questionText = "What occurs if water is left inside the tubes of a low-pressure chiller undergoing a deep evacuation while water pumps are turned off?",
            options = listOf("The water will evaporate and leave clean minerals", "Liquid refrigerant boiling at low absolute pressure pulls heat from the standing water, causing water in tubes to freeze, expand, and rupture the copper tubes", "The water will boil into high-pressure steam and burst the chiller", "The oil will turn into foam"),
            correctIndex = 1,
            explanation = "Stationary water quickly freezes into solid ice when refrigerant evaporates below 32°F; the expanding ice splits the copper heat exchanger tubes, flooding the chiller with water.",
            citation = "RSES Chiller Service Manual",
            isA2L = false
        ),
        Question(
            id = "t3-23",
            section = EPASection.TYPE_III,
            questionText = "What should be done if excessive moisture or water is found in the purge unit discharge of a low-pressure centrifugal chiller?",
            options = listOf("Ignore it because all purge units produce 5 gallons of water per day", "Immediately inspect the condenser and evaporator water tubes for pinhole leaks using hydrostatic or eddy current testing, and test refrigerant for acid", "Turn off the purge unit permanently", "Add antifreeze to the compressor oil"),
            correctIndex = 1,
            explanation = "High purge water volume indicates water is actively leaking from water tubes into the low-pressure refrigeration circuit through tube cracks or tube sheet seals.",
            citation = "Trane Field Service Alert",
            isA2L = false
        ),
        Question(
            id = "t3-24",
            section = EPASection.TYPE_III,
            questionText = "What testing method is standard for inspecting low-pressure centrifugal chiller heat exchanger tubes for internal pitting, corrosion, and wall thinning?",
            options = listOf("Tapping the tubes with a steel ball peen hammer", "Non-destructive Eddy Current Testing using an electromagnetic inspection probe pulled through each tube", "Visual inspection with an ordinary flashlight", "Filling the tubes with smoke"),
            correctIndex = 1,
            explanation = "Eddy current testing detects subtle electromagnetic variations caused by pitting, wall erosion, and cracks along the entire length of copper chiller tubes.",
            citation = "ASME Section V Nondestructive Examination",
            isA2L = false
        ),
        Question(
            id = "t3-25",
            section = EPASection.TYPE_III,
            questionText = "Where is the liquid refrigerant typically removed from when recovering from a low-pressure centrifugal chiller?",
            options = listOf("From the top of the purge unit", "From the lowest point of the evaporator shell liquid charging valve", "From the compressor discharge nozzle", "From the rupture disc vent line"),
            correctIndex = 1,
            explanation = "The evaporator shell sits at the bottom of the chiller assembly; liquid pools at the lowest point and is drained through the main liquid service access valve.",
            citation = "York Service Procedures",
            isA2L = false
        ),
        Question(
            id = "t3-26",
            section = EPASection.TYPE_III,
            questionText = "How is the remaining refrigerant vapor recovered from a low-pressure chiller after all liquid has been evacuated?",
            options = listOf("By opening all mechanical room doors", "By pumping vapor out through the recovery unit compressor, condensing it into a water-cooled recovery cylinder", "By pouring warm water into the refrigeration shell", "Vapor cannot be recovered and must be vented"),
            correctIndex = 1,
            explanation = "A low-pressure vapor recovery machine draws vapor from the highest system point (condenser), compresses it, and condenses it into an external recovery container.",
            citation = "40 CFR § 82.156(a)",
            isA2L = false
        ),
        Question(
            id = "t3-27",
            section = EPASection.TYPE_III,
            questionText = "Under 40 CFR § 82.157, what is the annual leak rate trigger threshold for a low-pressure centrifugal chiller containing 1,000 lbs of refrigerant used for comfort cooling in a commercial office building?",
            options = listOf("10%", "20%", "30%", "35%"),
            correctIndex = 0,
            explanation = "Comfort cooling systems containing 50 or more pounds of refrigerant have a statutory leak rate threshold of 10% under 40 CFR § 82.157(c)(2)(iii).",
            citation = "40 CFR § 82.157(c)(2)(iii)",
            isA2L = false
        ),
        Question(
            id = "t3-28",
            section = EPASection.TYPE_III,
            questionText = "Under 40 CFR § 82.157, what leak rate threshold applies to a low-pressure chiller used in an industrial manufacturing plant to freeze pharmaceutical products?",
            options = listOf("10%", "20%", "30%", "50%"),
            correctIndex = 2,
            explanation = "Industrial process refrigeration (IPR) equipment has a 30% statutory leak threshold under 40 CFR § 82.157(c)(2)(i).",
            citation = "40 CFR § 82.157(c)(2)(i)",
            isA2L = false
        ),
        Question(
            id = "t3-29",
            section = EPASection.TYPE_III,
            questionText = "How many days does an owner of a low-pressure comfort cooling chiller have to repair a leak that exceeds the 10% threshold?",
            options = listOf("10 days", "30 days", "90 days", "1 year"),
            correctIndex = 1,
            explanation = "The Clean Air Act Section 608 mandates that leaks exceeding the threshold must be repaired within 30 days of discovery.",
            citation = "40 CFR § 82.157(d)(1)",
            isA2L = false
        ),
        Question(
            id = "t3-30",
            section = EPASection.TYPE_III,
            questionText = "What is the primary function of the oil heater on a low-pressure centrifugal chiller?",
            options = listOf("To heat the mechanical room in winter", "To keep oil at ~130°F to boil out and prevent low-pressure refrigerant from condensing and dissolving into the lubricating oil sump during shutdown", "To melt wax in the purge unit", "To reduce motor amperage by 50%"),
            correctIndex = 1,
            explanation = "Low-pressure refrigerants have high solubility in lubricating oil at room temperature. Keeping oil hot prevents refrigerant dilution and bearing foaming.",
            citation = "Trane Centravac Service Manual",
            isA2L = false
        ),
        Question(
            id = "t3-31",
            section = EPASection.TYPE_III,
            questionText = "What happens if a centrifugal chiller compressor is started when cold oil contains a high concentration of dissolved low-pressure refrigerant?",
            options = listOf("The motor will run smoothly and silently", "Violent oil foaming occurs on startup, starving bearings of lubrication and causing rapid oil pump cavitation and bearing wipeout", "The evaporator coil will freeze immediately", "The rupture disc will burst instantly"),
            correctIndex = 1,
            explanation = "Rapid suction pressure drop flashes dissolved refrigerant, generating foam that strips oil films from precision hydrodynamic bearings, destroying them within seconds.",
            citation = "Carrier Chiller Reliability Manual",
            isA2L = false
        ),
        Question(
            id = "t3-32",
            section = EPASection.TYPE_III,
            questionText = "What is the typical atmospheric boiling point of refrigerant HCFC-123 at sea level (14.7 psia)?",
            options = listOf("-15.3°F", "32.0°F", "82.0°F (27.8°C)", "212.0°F"),
            correctIndex = 2,
            explanation = "HCFC-123 boils at approximately 82.0°F (27.8°C) at sea level, meaning it is a liquid at room temperature and operates in a vacuum in chillers.",
            citation = "HCFC-123 Thermodynamic Tables",
            isA2L = false
        ),
        Question(
            id = "t3-33",
            section = EPASection.TYPE_III,
            questionText = "What is the typical atmospheric boiling point of refrigerant CFC-11 at sea level?",
            options = listOf("-40.0°F", "74.9°F (23.8°C)", "120.0°F", "0.0°F"),
            correctIndex = 1,
            explanation = "CFC-11 has a sea-level atmospheric boiling point of 74.9°F, operating in deep vacuum on the low side of vintage centrifugal chillers.",
            citation = "CFC-11 Properties",
            isA2L = false
        ),
        Question(
            id = "t3-34",
            section = EPASection.TYPE_III,
            questionText = "Because low-pressure chillers operate with evaporator pressures in a vacuum, where will refrigerant leaks occur during normal operation?",
            options = listOf("Refrigerant will spray out of the evaporator into the room", "Ambient air and moisture will be drawn IN through gaskets, valve packings, and tube fittings into the low side", "Refrigerant will leak into the mechanical room ceiling", "Water will be forced into the oil filter"),
            correctIndex = 1,
            explanation = "Operating below atmospheric pressure creates a reverse pressure differential where outside air leaks into the machine rather than refrigerant escaping.",
            citation = "ASHRAE HVAC Systems and Equipment",
            isA2L = false
        ),
        Question(
            id = "t3-35",
            section = EPASection.TYPE_III,
            questionText = "What type of acid is formed when water leaks into a low-pressure chiller charged with CFC-11 or HCFC-123?",
            options = listOf("Acetic acid (vinegar)", "Hydrochloric acid (HCl) and hydrofluoric acid (HF)", "Sulfuric acid", "Citric acid"),
            correctIndex = 1,
            explanation = "Halogenated refrigerants undergo chemical hydrolysis in the presence of water and heat, forming aggressive mineral acids that corrode steel shells and copper tubes.",
            citation = "Carrier Technical Bulletin",
            isA2L = false
        ),
        Question(
            id = "t3-36",
            section = EPASection.TYPE_III,
            questionText = "What is the purpose of an oil distillation unit on a low-pressure recovery system?",
            options = listOf("To refine used motor oil into gasoline", "To boil off and separate dissolved refrigerant from recovered compressor lubricant so the oil can be safely handled or recycled", "To heat water for the chiller", "To cool the recovery compressor"),
            correctIndex = 1,
            explanation = "Low-pressure recovery machines use heat to distill refrigerant vapor out of viscous compressor oil, ensuring minimal refrigerant is discarded with waste oil.",
            citation = "AHRI Standard 740 Annex",
            isA2L = false
        ),
        Question(
            id = "t3-37",
            section = EPASection.TYPE_III,
            questionText = "Under 40 CFR § 82.156, what is required before opening an access port on a low-pressure centrifugal chiller for maintenance?",
            options = listOf("The mechanical room lights must be turned off", "The internal pressure must be brought to atmospheric pressure (0 psig) or slightly positive using dry nitrogen or warm water after statutory recovery", "The chiller must be filled with cold tap water", "The technician must remove the rupture disc"),
            correctIndex = 1,
            explanation = "Opening a vessel in a vacuum draws ambient air and humidity inside. Pressure must be equalized to atmospheric pressure (0 psig) before unsealing.",
            citation = "40 CFR § 82.156(b)",
            isA2L = false
        ),
        Question(
            id = "t3-38",
            section = EPASection.TYPE_III,
            questionText = "What is the primary function of a water box gasket on a centrifugal chiller shell?",
            options = listOf("To separate high-pressure refrigerant from low-pressure refrigerant", "To provide a leak-tight seal between the water box heads and the tube sheet, keeping circulating chilled/condenser water inside the tubes", "To vent air from the evaporator", "To filter debris from the cooling tower"),
            correctIndex = 1,
            explanation = "Water box gaskets seal water flow across tube passes and prevent external water leakage or water bypassing between multi-pass channels.",
            citation = "Chiller Overhaul Guidelines",
            isA2L = false
        ),
        Question(
            id = "t3-39",
            section = EPASection.TYPE_III,
            questionText = "What is a 'tube sheet' in a shell-and-tube centrifugal chiller?",
            options = listOf("A paper diagram of the chiller piping", "A thick steel plate with drilled holes through which copper tubes are rolled and expanded to separate the refrigerant shell from the water chambers", "A metal sheet covering the compressor motor", "A plastic baffle in the cooling tower"),
            correctIndex = 1,
            explanation = "The tube sheet forms the structural boundary between the refrigerant bath inside the shell and the circulating water inside the heads.",
            citation = "ASME Section VIII Pressure Vessels",
            isA2L = false
        ),
        Question(
            id = "t3-40",
            section = EPASection.TYPE_III,
            questionText = "What tool is used to mechanically secure and seal copper tubes into the tube sheet during chiller manufacturing or retubing?",
            options = listOf("A pipe wrench", "An electronic mechanical tube expander (tube roller)", "A soldering iron", "A hot glue gun"),
            correctIndex = 1,
            explanation = "Tube rolling tools expand copper tubes radially into grooved holes in the tube sheet, creating a cold-worked, metal-to-metal high-pressure mechanical seal.",
            citation = "TEMA Standards (Tubular Exchanger Manufacturers Association)",
            isA2L = false
        ),
        Question(
            id = "t3-41",
            section = EPASection.TYPE_III,
            questionText = "What symptom indicates that an internal chiller water tube has ruptured while the system is dormant under vacuum?",
            options = listOf("The evaporator shell pressure rises to water loop pressure, and the oil sump and shell fill with water", "The rupture disc bursts immediately", "The chiller water temperature drops to -100°F", "The mechanical room lights flicker"),
            correctIndex = 0,
            explanation = "Because building water loop pressure (typically 30 to 80 psig) is much higher than chiller vacuum, water floods into the refrigerant shell through any tube rupture.",
            citation = "Trane Centravac Service Guide",
            isA2L = false
        ),
        Question(
            id = "t3-42",
            section = EPASection.TYPE_III,
            questionText = "What emergency action must a technician take if water floods into the refrigerant shell of a low-pressure chiller?",
            options = listOf("Turn on the compressor immediately to pump it out", "Immediately shut down circulating water pumps, isolate the water loops, drain the water, and recover the remaining refrigerant before severe acid corrosion occurs", "Pour bleach into the chiller shell", "Vent all gas to the atmosphere"),
            correctIndex = 1,
            explanation = "Standing water and refrigerant rapidly generate severe acid that etches steel shells and destroys motor windings. Fast drainage and recovery are imperative.",
            citation = "York Service Bulletins",
            isA2L = false
        ),
        Question(
            id = "t3-43",
            section = EPASection.TYPE_III,
            questionText = "What type of recovery cylinder is used for storing recovered low-pressure refrigerants like HCFC-123?",
            options = listOf("A high-pressure DOT-4BA cylinder rated for 400 psig", "A low-pressure recovery cylinder or DOT-4BW drum rated for liquid storage with appropriate pressure relief protection", "A cardboard barrel lined with plastic", "An open steel bucket"),
            correctIndex = 1,
            explanation = "Low-pressure refrigerants are liquids at room temperature and require approved storage containers with pressure relief devices set to appropriate DOT ratings.",
            citation = "49 CFR § 178.61",
            isA2L = false
        ),
        Question(
            id = "t3-44",
            section = EPASection.TYPE_III,
            questionText = "What is the maximum test pressure permitted when hydrostatic leak testing the shell of a low-pressure centrifugal chiller?",
            options = listOf("Never exceed the design test pressure stamped on the ASME vessel nameplate (typically 10 to 15 psig on low-pressure shells)", "500 psig", "100 psig", "50 psig"),
            correctIndex = 0,
            explanation = "Vessel shells have thin walls engineered for vacuum; exceeding nameplate test pressure can permanently distort or rupture the steel shell.",
            citation = "ASME Section VIII Pressure Vessel Code",
            isA2L = false
        ),
        Question(
            id = "t3-45",
            section = EPASection.TYPE_III,
            questionText = "How can non-condensable air be purged from a low-pressure refrigerant recovery cylinder before transferring refrigerant back into a chiller?",
            options = listOf("By opening the liquid valve upside down", "By measuring ambient temperature, determining saturation pressure on the P-T chart, and slowly venting excess vapor pressure from the top vapor port until saturation pressure is reached", "By running cold tap water over the top of the tank", "By shaking the cylinder vigorously"),
            correctIndex = 1,
            explanation = "Venting excess pressure from the vapor space until internal pressure matches saturation pressure at that temperature purges air while minimizing refrigerant loss.",
            citation = "AHRI Guideline K",
            isA2L = false
        ),
        Question(
            id = "t3-46",
            section = EPASection.TYPE_III,
            questionText = "What safety classification does HFC-245fa hold under ASHRAE Standard 34?",
            options = listOf("A1 (lower toxicity, non-flammable)", "A2L", "B1", "B3"),
            correctIndex = 0,
            explanation = "HFC-245fa is classified as Class A1: non-flammable and lower toxicity, commonly used in waste heat recovery and low-pressure chillers.",
            citation = "ASHRAE Standard 34",
            isA2L = false
        ),
        Question(
            id = "t3-47",
            section = EPASection.TYPE_III,
            questionText = "What is the atmospheric boiling point of HFC-245fa at sea level?",
            options = listOf("59.5°F (15.3°C)", "-15.0°F", "150.0°F", "32.0°F"),
            correctIndex = 0,
            explanation = "HFC-245fa boils at approximately 59.5°F at standard atmospheric pressure, functioning as a low-pressure liquid under ambient conditions.",
            citation = "HFC-245fa Properties Table",
            isA2L = false
        ),
        Question(
            id = "t3-48",
            section = EPASection.TYPE_III,
            questionText = "Under ASHRAE Standard 15, what is the maximum volume of refrigerant that can be safely discharged into a designated mechanical room without a direct outdoor vent pipe?",
            options = listOf("100 pounds", "Zero pounds; all pressure relief devices must vent directly outdoors to an approved safe discharge location", "50 pounds", "10 pounds"),
            correctIndex = 1,
            explanation = "Standard 15 strictly mandates that relief devices on chillers with charges over minimal thresholds must pipe directly outdoors to the atmosphere.",
            citation = "ASHRAE Standard 15 § 9.7.1",
            isA2L = false
        ),
        Question(
            id = "t3-49",
            section = EPASection.TYPE_III,
            questionText = "What happens if a technician replaces a blown 15 psig rupture disc on a low-pressure chiller with a generic 50 psig rupture disc?",
            options = listOf("The chiller will run 20% more efficiently", "The thin-walled evaporator shell loses its certified safety protection and can explode catastrophically before the 50 psig disc bursts", "The purge unit will run backward", "The water will freeze inside the tubes"),
            correctIndex = 1,
            explanation = "Rupture discs are calibrated safety devices sized to the vessel's maximum allowable working pressure (MAWP). An overrated disc creates fatal explosion hazards.",
            citation = "ASME Boiler and Pressure Vessel Code",
            isA2L = false
        ),
        Question(
            id = "t3-50",
            section = EPASection.TYPE_III,
            questionText = "What is the purpose of an evacuation vacuum rise (decay) test on a low-pressure chiller after reaching 25 mm Hg absolute?",
            options = listOf("To check if the compressor motor will turn by hand", "To prove that the vessel is leak-tight and free of moisture before recharging; a continuous rise to atmospheric pressure proves an active leak exists", "To test the accuracy of the thermometer", "To clean the condenser water tubes"),
            correctIndex = 1,
            explanation = "Isolating the vacuum pump and monitoring pressure rise verifies whether leaks remain; rising and stabilizing indicates residual moisture; rising to atmosphere indicates a leak.",
            citation = "EPA 608 Evacuation Verification Standards",
            isA2L = false
        ),
        Question(
            id = "t3-51",
            section = EPASection.TYPE_III,
            questionText = "What is the primary indicator that a purge unit on a low-pressure chiller is operating properly?",
            options = listOf("The purge pump discharges large clouds of dense white smoke", "The purge unit periodically vents small amounts of air and non-condensables while condensing and returning liquid refrigerant to the evaporator with minimal loss", "The purge unit runs 24 hours a day continuously without stopping", "The purge unit discharges pure liquid refrigerant into a bucket"),
            correctIndex = 1,
            explanation = "High-efficiency purge units separate non-condensables and expel air and moisture while returning cleaned liquid refrigerant back to the evaporator.",
            citation = "ASHRAE Equipment Handbook",
            isA2L = false
        ),
        Question(
            id = "t3-52",
            section = EPASection.TYPE_III,
            questionText = "What does it mean when the purge run-time meter on a low-pressure chiller shows a sudden sharp increase in daily operating hours?",
            options = listOf("The mechanical room humidity has dropped", "A new or worsening air leak has developed in the chiller low-side shell, pulling ambient air into the machine", "The cooling tower water has become too cold", "The compressor motor is drawing less current"),
            correctIndex = 1,
            explanation = "Purge run hours track non-condensable accumulation. A spike in daily run hours indicates a significant air leak into the vacuum shell.",
            citation = "Trane Centravac Diagnostic Guide",
            isA2L = false
        ),
        Question(
            id = "t3-53",
            section = EPASection.TYPE_III,
            questionText = "Where are the most common leak locations on a low-pressure centrifugal chiller?",
            options = listOf("In the middle of straight copper pipe runs", "At flange gaskets, shaft seals, water box gaskets, sensor wells, and valve stem packings", "Inside the solid iron motor rotor", "In the building cooling tower nozzles"),
            correctIndex = 1,
            explanation = "Chillers have numerous bolted flanges, access covers, and dynamic shaft seals that flex under vibration and thermal cycles, causing gasket leaks.",
            citation = "Carrier Chiller Service Manual",
            isA2L = false
        ),
        Question(
            id = "t3-54",
            section = EPASection.TYPE_III,
            questionText = "What safety precaution must be taken before removing the inspection cover plate on a centrifugal chiller oil sump?",
            options = listOf("Drain all cooling tower water", "Verify the internal shell pressure is atmospheric (0 psig), recover refrigerant from the oil, and lock out electrical power", "Heat the sump with an open flame torch to 200°F", "Run the water pumps at double speed"),
            correctIndex = 1,
            explanation = "Opening an un-evacuated sump releases toxic vapor or pulls in air. Pressure must be 0 psig and electrical circuits locked out under OSHA LOTO.",
            citation = "OSHA 1910.147 LOTO Standards",
            isA2L = false
        ),
        Question(
            id = "t3-55",
            section = EPASection.TYPE_III,
            questionText = "What type of pump is typically used inside a purge unit to exhaust non-condensable gases from the condensing chamber?",
            options = listOf("A hydraulic oil gear pump", "A small positive-displacement vacuum pump or diaphragm pump", "A high-pressure centrifugal water pump", "A jet ejector powered by steam"),
            correctIndex = 1,
            explanation = "Purge units utilize small vacuum pumps to pump separated non-condensable air against atmospheric pressure to discharge outdoors.",
            citation = "Purge Unit Maintenance Handbook",
            isA2L = false
        ),
        Question(
            id = "t3-56",
            section = EPASection.TYPE_III,
            questionText = "Under ASHRAE Standard 15, what is the maximum permissible noise level for emergency machinery room ventilation alarms?",
            options = listOf("At least 15 dBA above ambient noise levels and visible flashing strobe lights at every entrance door", "No louder than a whisper", "Sound alarms are strictly prohibited in mechanical rooms", "Continuous siren at 180 dBA"),
            correctIndex = 0,
            explanation = "Alarms must clearly exceed ambient mechanical room noise (at least 15 dBA above ambient) accompanied by visual strobes to warn personnel before entering.",
            citation = "ASHRAE Standard 15 § 8.11.8",
            isA2L = false
        ),
        Question(
            id = "t3-57",
            section = EPASection.TYPE_III,
            questionText = "Why must technicians be trained in the specific operation of breathing apparatus (SCBA) when working in mechanical rooms with Group B refrigerants?",
            options = listOf("Because Group B refrigerants are sweet smelling and addictive", "Because Group B refrigerants possess higher chemical toxicity with permissible exposure limits (PEL/TLV) under 400 ppm, presenting severe health risks during leaks", "Because SCBA gear protects against loud motor noise", "Because OSHA mandates SCBA gear in all commercial offices"),
            correctIndex = 1,
            explanation = "Group B refrigerants (e.g. HCFC-123 at 50 ppm TLV) are toxic at low concentrations; SCBA gear ensures respiratory protection in emergency leak events.",
            citation = "OSHA 1910.134 Respiratory Protection",
            isA2L = false
        ),
        Question(
            id = "t3-58",
            section = EPASection.TYPE_III,
            questionText = "What is the function of the rupture disc pressure relief sensor or burst indicator switch installed on a chiller vent line?",
            options = listOf("To turn on the chiller water pump", "To alert the building automation system (BAS) immediately if the rupture disc has burst, signaling that a major refrigerant release is occurring", "To measure atmospheric humidity", "To automatically weld the disc back together"),
            correctIndex = 1,
            explanation = "Burst sensors signal the control system and facilities team instantly upon rupture disc failure so emergency ventilation and leak protocols activate.",
            citation = "ASHRAE Standard 15 Guidelines",
            isA2L = false
        ),
        Question(
            id = "t3-59",
            section = EPASection.TYPE_III,
            questionText = "What is the purpose of an auxiliary carbon canister adsorption system installed on a modern purge unit exhaust line?",
            options = listOf("To filter dirt from the mechanical room air", "To capture and adsorb trace refrigerant molecules from expelled non-condensable air, minimizing environmental emissions to near zero", "To make the exhaust air smell like pine trees", "To add carbon dioxide to the atmosphere"),
            correctIndex = 1,
            explanation = "Activated carbon canisters scrub residual refrigerant vapors from purge exhaust air before discharging outdoors, ensuring compliance with strict EPA clean air rules.",
            citation = "Trane EarthWise Purge System Specifications",
            isA2L = false
        ),
        Question(
            id = "t3-60",
            section = EPASection.TYPE_III,
            questionText = "How is the carbon bed regenerated in a regenerative purge unit on a low-pressure centrifugal chiller?",
            options = listOf("By throwing the carbon bed into the dumpster", "By applying heat and drawing a deep vacuum to desorb captured refrigerant and return it to the chiller evaporator", "By washing the carbon with tap water", "By burning the carbon with an acetylene torch"),
            correctIndex = 1,
            explanation = "Regenerative purge units automatically heat the carbon canister under vacuum, desorbing trapped refrigerant and cycling it back to the chiller.",
            citation = "Carrier Chiller Technical Guide",
            isA2L = false
        ),
        Question(
            id = "t3-61",
            section = EPASection.TYPE_III,
            questionText = "What type of compressor is most commonly used in large-capacity low-pressure water chillers (200 to 2,000+ tons)?",
            options = listOf("Single-acting reciprocating piston compressor", "Centrifugal dynamic compressor with single or multi-stage impellers", "Scroll compressor", "Rotary vane compressor"),
            correctIndex = 1,
            explanation = "Low-pressure refrigerants require moving enormous volumes of low-density vapor, which is efficiently handled by high-volume centrifugal dynamic impellers.",
            citation = "ASHRAE Equipment Handbook",
            isA2L = false
        ),
        Question(
            id = "t3-62",
            section = EPASection.TYPE_III,
            questionText = "How is capacity control achieved on a large centrifugal chiller compressor?",
            options = listOf("By turning the compressor motor on and off every 30 seconds", "By adjusting variable inlet guide vanes (pre-rotation vanes) and modulating compressor variable frequency drive (VFD) motor speed", "By opening manual valves on the roof", "By draining refrigerant into buckets"),
            correctIndex = 1,
            explanation = "Inlet guide vanes swirl incoming vapor into the impeller, throttling mass flow, while VFD controllers modulate impeller tip speed to match cooling demand.",
            citation = "Trane Centravac Engineering Bulletin",
            isA2L = false
        ),
        Question(
            id = "t3-63",
            section = EPASection.TYPE_III,
            questionText = "What dangerous condition can occur in a centrifugal chiller compressor if condensing pressure rises too high relative to evaporator pressure at low load?",
            options = listOf("Compressor cavitation", "Compressor surging, characterized by violent aerodynamic flow reversal, loud thumping noises, and intense vibration", "The motor runs backward", "The oil turns into ice"),
            correctIndex = 1,
            explanation = "Surge occurs when the impeller cannot overcome high condenser lift pressure; high-pressure vapor reverses flow back through the impeller, causing severe mechanical strain.",
            citation = "Centrifugal Chiller Aerodynamics Manual",
            isA2L = false
        ),
        Question(
            id = "t3-64",
            section = EPASection.TYPE_III,
            questionText = "How can a centrifugal compressor surging condition be eliminated by an operator or automated control system?",
            options = listOf("Increase chilled water supply temperature or decrease cooling tower condenser water temperature to lower the head pressure lift", "Shut off the chiller water circulating pumps immediately", "Add 200 lbs of liquid refrigerant to the suction line", "Turn off the mechanical room lights"),
            correctIndex = 0,
            explanation = "Lowering condenser water temperature or raising evaporator load reduces the required compression lift pressure, moving the operating point out of the surge region.",
            citation = "York Chiller Engineering Bulletin",
            isA2L = false
        ),
        Question(
            id = "t3-65",
            section = EPASection.TYPE_III,
            questionText = "What is the primary indicator of a condenser tube scaling problem in a water-cooled low-pressure chiller?",
            options = listOf("Low evaporator pressure", "Elevated condenser head pressure combined with a high approach temperature (condenser refrigerant saturation temperature minus condenser water leaving temperature)", "The purge unit runs backward", "Water flows out of the rupture disc"),
            correctIndex = 1,
            explanation = "Mineral scale on copper tube interiors acts as an insulating barrier, reducing heat transfer and causing a high approach temperature between refrigerant and leaving water.",
            citation = "Cooling Tower Water Treatment Guidelines",
            isA2L = false
        ),
        Question(
            id = "t3-66",
            section = EPASection.TYPE_III,
            questionText = "What is the normal design 'approach temperature' for a clean water-cooled chiller condenser at full load?",
            options = listOf("0°F", "Less than 1°F to 3°F (typically under 5°F)", "20°F to 30°F", "50°F"),
            correctIndex = 1,
            explanation = "A clean condenser achieves approach temperatures below 3°F to 5°F. Approach temperatures rising above 8°F to 10°F indicate tube fouling or non-condensable gas.",
            citation = "ASHRAE HVAC Systems Handbook",
            isA2L = false
        ),
        Question(
            id = "t3-67",
            section = EPASection.TYPE_III,
            questionText = "How are mineral scale deposits cleaned from the inside of low-pressure chiller condenser water tubes?",
            options = listOf("By running sand through the water lines", "By mechanically rodding the tubes with rotating nylon or brass brushes, or circulating an approved chemical descaling acid wash", "By heating the tubes with a weed burner", "By spraying compressed air through the tubes"),
            correctIndex = 1,
            explanation = "Condenser water heads are unbolted to brush-clean copper tubes mechanically, or mild inhibited acid washes are circulated to dissolve calcium carbonate scale.",
            citation = "Carrier Chiller Maintenance Procedures",
            isA2L = false
        ),
        Question(
            id = "t3-68",
            section = EPASection.TYPE_III,
            questionText = "Why are low-pressure chiller evaporator water tubes less prone to mineral scaling than condenser water tubes?",
            options = listOf("Because evaporator tubes are made of solid gold", "Because the chilled water loop is a closed, sealed piping circuit with minimal mineral makeup, whereas cooling towers evaporate water and concentrate minerals", "Because refrigerant dissolves all scale", "Because chilled water is always distilled"),
            correctIndex = 1,
            explanation = "Closed chilled water loops recirculate clean treated water; open cooling tower condenser loops constantly evaporate water, precipitating calcium and silica scale.",
            citation = "Commercial Water Treatment Handbook",
            isA2L = false
        ),
        Question(
            id = "t3-69",
            section = EPASection.TYPE_III,
            questionText = "What is the purpose of sacrificial zinc anodes installed inside the cast-iron water boxes of a chiller?",
            options = listOf("To filter biological algae from the water", "To provide galvanic cathodic protection, corroding sacrificially to protect the copper tubes and tube sheet from galvanic electrochemical corrosion", "To add zinc minerals to the drinking water", "To hold the water box gasket in place"),
            correctIndex = 1,
            explanation = "Zinc has a lower electrochemical potential than copper or iron; it corrodes preferentially, shielding expensive heat exchanger alloys from galvanic attack.",
            citation = "NACE International Cathodic Protection Standards",
            isA2L = false
        ),
        Question(
            id = "t3-70",
            section = EPASection.TYPE_III,
            questionText = "How often should sacrificial zinc anodes in chiller water boxes be inspected and replaced?",
            options = listOf("Every 50 years", "Annually during scheduled winter maintenance, or replaced whenever eroded past 50% of original mass", "Every week", "They never need replacement"),
            correctIndex = 1,
            explanation = "Anodes are inspected during annual water box overhauls and replaced before complete depletion leaves tube sheets vulnerable to pitting.",
            citation = "ASHRAE Maintenance Guidelines",
            isA2L = false
        ),
        Question(
            id = "t3-71",
            section = EPASection.TYPE_III,
            questionText = "What is the consequence of opening a low-pressure chiller for major overhaul without first running water pumps to drain tubes?",
            options = listOf("The mechanical room will flood", "Uncontrolled internal moisture condensation can occur, and any residual liquid refrigerant will flash-boil and freeze water remaining in undrained tubes", "The motor will reverse polarity", "The compressor impeller will warp"),
            correctIndex = 1,
            explanation = "Tubes must be drained or circulating continuously during opening and evacuation to avoid tube freeze-up and prevent atmospheric moisture absorption.",
            citation = "York Overhaul Manual",
            isA2L = false
        ),
        Question(
            id = "t3-72",
            section = EPASection.TYPE_III,
            questionText = "What is the purpose of an external vacuum pump used in conjunction with a low-pressure chiller recovery machine?",
            options = listOf("To circulate compressor oil", "To pull down the final deep vacuum from 4 inches Hg down to the statutory 25 mm Hg absolute requirement, where recovery compressors lose efficiency", "To inflate the rupture disc", "To fill the chiller with nitrogen"),
            correctIndex = 1,
            explanation = "Standard recovery compressors lose volumetric efficiency at high vacuums. A secondary high-vacuum pump achieves the mandatory 25 mm Hg absolute evacuation depth.",
            citation = "AHRI Standard 740 Testing Protocols",
            isA2L = false
        ),
        Question(
            id = "t3-73",
            section = EPASection.TYPE_III,
            questionText = "Under 40 CFR § 82.156 Table 1, what must be done if a low-pressure appliance cannot be evacuated to 25 mm Hg absolute due to an active leak in the shell?",
            options = listOf("The remaining refrigerant may be vented into the room", "The system must be evacuated to atmospheric pressure (0 psig) before unsealing the vessel for repair", "The technician must pour motor oil into the leak", "The technician must pressurize with 500 psig air"),
            correctIndex = 1,
            explanation = "If major leaks prevent drawing the 25 mm Hg vacuum without drawing ambient air into the recovery cylinder, the appliance must be evacuated to 0 psig before opening.",
            citation = "40 CFR § 82.156(a)(3)",
            isA2L = false
        ),
        Question(
            id = "t3-74",
            section = EPASection.TYPE_III,
            questionText = "What safety classification does refrigerant CFC-11 hold under ASHRAE Standard 34?",
            options = listOf("A1", "A2L", "B1", "B2"),
            correctIndex = 0,
            explanation = "CFC-11 is classified as Class A1: lower toxicity and non-flammable under standard conditions, but possesses an Ozone Depletion Potential of 1.0.",
            citation = "ASHRAE Standard 34",
            isA2L = false
        ),
        Question(
            id = "t3-75",
            section = EPASection.TYPE_III,
            questionText = "What is the primary factor limiting the maximum allowable pressure in a low-pressure chiller rupture disc assembly?",
            options = listOf("The strength of the mechanical room floor", "The design burst strength of the thin-walled evaporator shell (MAWP typically 15 psig)", "The size of the cooling tower", "The electrical rating of the water pump"),
            correctIndex = 1,
            explanation = "Evaporator shells are engineered for low-pressure vacuum service; their structural geometry cannot withstand internal pressures above 15 psig.",
            citation = "ASME Boiler and Pressure Vessel Code Section VIII",
            isA2L = false
        ),
        Question(
            id = "t3-76",
            section = EPASection.TYPE_III,
            questionText = "What type of relief device is used in series with a rupture disc on certain low-pressure chillers to prevent continuous venting after pressure relief?",
            options = listOf("A spring-loaded pressure relief valve mounted downstream of the rupture disc with an interspace pressure gauge", "A manual brass gate valve", "A check valve that vents into the office", "A solid copper cap soldered onto the pipe"),
            correctIndex = 0,
            explanation = "An ASME spring-loaded relief valve downstream of the rupture disc reseats once pressure drops, preventing total loss of charge; a telltale gauge monitors interspace leaks.",
            citation = "ASHRAE Standard 15 § 9.4.3",
            isA2L = false
        ),
        Question(
            id = "t3-77",
            section = EPASection.TYPE_III,
            questionText = "What does pressure registered on the telltale gauge mounted between a rupture disc and downstream relief valve indicate?",
            options = listOf("The downstream relief valve is working properly", "The rupture disc has developed a pinhole leak or has burst, allowing pressure to build up in the interspace and negating safety calibration", "The chiller is running at maximum efficiency", "The cooling tower water is cold"),
            correctIndex = 1,
            explanation = "Pressure in the cavity means the disc has leaked or ruptured; backpressure in the interspace alters the burst rating, requiring immediate disc replacement.",
            citation = "ASHRAE Standard 15 Safety Directives",
            isA2L = false
        ),
        Question(
            id = "t3-78",
            section = EPASection.TYPE_III,
            questionText = "When leak checking a low-pressure centrifugal chiller using dry nitrogen, why must the nitrogen cylinder always have a calibrated regulator set to 10 psig or lower?",
            options = listOf("To prevent nitrogen from turning into a liquid", "Because unregulated nitrogen (at 2,000+ psig) will instantly burst the 15 psig rupture disc, venting all nitrogen and remaining refrigerant violently", "To keep the mechanical room quiet", "Because nitrogen reacts with brass above 15 psig"),
            correctIndex = 1,
            explanation = "An unregulated pressure surge instantly ruptures the fragile 15 psig burst plate, causing costly emergency venting and replacement downtime.",
            citation = "RSES Chiller Safety Practice",
            isA2L = false
        ),
        Question(
            id = "t3-79",
            section = EPASection.TYPE_III,
            questionText = "What method should be used to pinpoint small leaks around low-pressure chiller valve stems and flange gaskets when pressurized to 8 psig with nitrogen?",
            options = listOf("An open propane torch", "An approved non-corrosive soap bubble leak detection solution or ultrasonic acoustic leak detector", "Pouring battery acid over the joint", "Listening with a stethoscope while the compressor runs"),
            correctIndex = 1,
            explanation = "Soap bubble solutions and ultrasonic detectors pinpoint small leaks under low positive pressure (5-10 psig) without damaging delicate gasket materials.",
            citation = "Trane Field Service Manual",
            isA2L = false
        ),
        Question(
            id = "t3-80",
            section = EPASection.TYPE_III,
            questionText = "Why should electronic halide leak detectors NEVER be used to search for leaks in low-pressure chillers charged with HFO-1233zd?",
            options = listOf("Because HFO-1233zd is invisible to electronic sensors", "Because halide torches only detect chlorine and use an open flame, which can produce toxic decomposition products", "Because HFO-1233zd is non-fluorinated", "Because halide torches only work in sub-zero temperatures"),
            correctIndex = 1,
            explanation = "Halide torches require chlorine to produce a green flame; modern HFOs lack active ozone-depleting chlorine and open flames generate toxic HF vapors.",
            citation = "EPA 608 Field Diagnostic Guide",
            isA2L = false
        ),
        Question(
            id = "t3-81",
            section = EPASection.TYPE_III,
            questionText = "What is the primary operational hazard of introducing liquid refrigerant directly onto the bare copper water tubes of an empty low-pressure chiller under deep vacuum?",
            options = listOf("The copper tubes will expand and become loose", "Liquid refrigerant flash-boils violently at sub-zero temperatures, freezing water droplets in tubes and creating thermal shock cracks in the copper", "The oil will dissolve into the water", "The compressor impeller will rotate backward"),
            correctIndex = 1,
            explanation = "Liquid in deep vacuum evaporates at extreme sub-zero temperatures; localized freezing and thermal shock can crack tubes or break rolled tube sheet joints.",
            citation = "Carrier Chiller Service Guidelines",
            isA2L = false
        ),
        Question(
            id = "t3-82",
            section = EPASection.TYPE_III,
            questionText = "To what vacuum level must a low-pressure centrifugal chiller be evacuated before initiating the holding period for a moisture dehydration test?",
            options = listOf("0 psig", "500 microns (approx. 0.5 mm Hg absolute) or the statutory 25 mm Hg absolute", "15 inches Hg vacuum", "5 inches Hg vacuum"),
            correctIndex = 1,
            explanation = "Commercial standards recommend pulling down to 500 microns to ensure all liquid water is vaporized and evacuated before recharging.",
            citation = "ASHRAE Dehydration Guidelines",
            isA2L = false
        ),
        Question(
            id = "t3-83",
            section = EPASection.TYPE_III,
            questionText = "What is the atmospheric boiling point of refrigerant HFO-1233zd(E) at sea level (14.7 psia)?",
            options = listOf("66.2°F (19.0°C)", "-15.0°F", "120.0°F", "32.0°F"),
            correctIndex = 0,
            explanation = "HFO-1233zd(E) boils at approximately 66.2°F (19.0°C) at atmospheric pressure, making it a liquid at room temperature and ideal for low-pressure chillers.",
            citation = "Honeywell Solstice zd Technical Data",
            isA2L = false
        ),
        Question(
            id = "t3-84",
            section = EPASection.TYPE_III,
            questionText = "Under 40 CFR § 82.157, what documentation must be kept by an owner when an approved retrofit or retirement plan is established for a low-pressure chiller exceeding leak thresholds?",
            options = listOf("A blueprint of the building roof", "A copy of the retrofit plan showing the date, engineering contractor, target completion date within 1 year, and certified calculations kept on site for 3 years", "A photo of the chiller serial plate", "No documentation is required if under 2,000 lbs"),
            correctIndex = 1,
            explanation = "Owners must retain the dated, signed retrofit plan and all subsequent verification records for three (3) years on site for EPA inspection.",
            citation = "40 CFR § 82.157(h)",
            isA2L = false
        ),
        Question(
            id = "t3-85",
            section = EPASection.TYPE_III,
            questionText = "What is the primary benefit of variable frequency drives (VFDs) on centrifugal chiller compressors?",
            options = listOf("They eliminate the need for lubricating oil", "They modulate compressor impeller speed to match part-load cooling demand, dramatically reducing energy consumption during low-lift or off-peak conditions", "They prevent the rupture disc from bursting", "They make the refrigerant non-toxic"),
            correctIndex = 1,
            explanation = "Centrifugal compressors running at reduced tip speed during low condenser water temperatures save up to 30-40% of annual chiller electrical energy.",
            citation = "DOE Chiller Efficiency Standards",
            isA2L = false
        ),
        Question(
            id = "t3-86",
            section = EPASection.TYPE_III,
            questionText = "What type of bearing lubrication system is used in modern magnetic-bearing oil-free centrifugal chillers (such as Turbocor compressors)?",
            options = listOf("Vegetable oil circulation", "Permanent electromagnetic bearings that suspend the rotating compressor shaft in a magnetic field, completely eliminating lubricating oil from the chiller shell", "Grease fittings that must be pumped daily", "Water-lubricated ceramic balls"),
            correctIndex = 1,
            explanation = "Magnetic bearings float the rotor without physical contact, eliminating oil management, oil heaters, and oil contamination of heat exchanger surfaces.",
            citation = "Danfoss Turbocor Technical Manual",
            isA2L = false
        ),
        Question(
            id = "t3-87",
            section = EPASection.TYPE_III,
            questionText = "Why does eliminating lubricating oil from a centrifugal chiller improve its overall thermal heat transfer efficiency by 8% to 15%?",
            options = listOf("Because oil makes the refrigerant evaporate too quickly", "Because lubricating oil circulates and coats heat exchanger tube surfaces with an insulating film; eliminating oil ensures clean metal-to-refrigerant contact", "Because magnetic fields cool the water directly", "Because oil-free chillers run on pure nitrogen"),
            correctIndex = 1,
            explanation = "Oil coating inside tubes impairs heat transfer. Oil-free chillers maintain pristine copper surfaces, optimizing thermodynamic heat transfer rates.",
            citation = "ASHRAE Journal Technical Research",
            isA2L = false
        ),
        Question(
            id = "t3-88",
            section = EPASection.TYPE_III,
            questionText = "What is the function of the thrust bearing on a conventional centrifugal chiller compressor?",
            options = listOf("To support the weight of the motor casing", "To absorb the severe axial (longitudinal) forces exerted on the rotating impeller shaft caused by aerodynamic pressure differences between inlet and discharge", "To pump refrigerant into the condenser", "To generate electricity for the purge unit"),
            correctIndex = 1,
            explanation = "Centrifugal impellers experience high axial thrust forces pushing toward the suction; hydrodynamic thrust bearings absorb these forces to prevent shaft displacement.",
            citation = "Compressor Mechanical Engineering Manual",
            isA2L = false
        ),
        Question(
            id = "t3-89",
            section = EPASection.TYPE_III,
            questionText = "What happens if a centrifugal chiller compressor experiences thrust bearing failure while operating at 10,000 RPM?",
            options = listOf("The compressor will coast to a gentle stop", "The rotating impeller will shift axially and collide with the stationary compressor housing shroud, resulting in catastrophic high-speed mechanical destruction", "The chiller water will turn red", "The electrical breaker will reset automatically"),
            correctIndex = 1,
            explanation = "Axial clearance between high-speed impellers and housings is measured in thousandths of an inch; bearing failure causes explosive metal-to-metal collision.",
            citation = "Chiller Overhaul Failure Analysis",
            isA2L = false
        ),
        Question(
            id = "t3-90",
            section = EPASection.TYPE_III,
            questionText = "What safety check must be performed on a centrifugal chiller oil pump before starting the main drive motor?",
            options = listOf("Check that the cooling tower water is hot", "Verify that the pre-lube oil pump runs, establishes design oil pressure differential, and confirms adequate oil temperature (130°F) before main motor start", "Verify that the rupture disc is removed", "Drain 5 gallons of oil onto the floor"),
            correctIndex = 1,
            explanation = "Chiller control logic enforces a pre-lubrication period to ensure bearings are fully bathed in pressurized, warm oil before the main high-horsepower motor energizes.",
            citation = "Carrier Pre-Start Checklist",
            isA2L = false
        ),
        Question(
            id = "t3-91",
            section = EPASection.TYPE_III,
            questionText = "Under what condition is a technician permitted to reuse recovered HCFC-123 in a different owner's centrifugal chiller?",
            options = listOf("Whenever the second owner signs a verbal agreement", "Only after the refrigerant has been sent to an EPA-certified reclaimer and chemically analyzed to meet AHRI Standard 700 purity standards", "If the technician filters the refrigerant through an inline drier twice", "Refrigerant can never be reused under any circumstance"),
            correctIndex = 1,
            explanation = "Transferring refrigerant between different owners legally requires full reclamation to AHRI 700 purity specifications by an EPA-certified reclaimer.",
            citation = "40 CFR § 82.154(g)",
            isA2L = false
        ),
        Question(
            id = "t3-92",
            section = EPASection.TYPE_III,
            questionText = "Under Clean Air Act Section 608, what is the penalty for falsifying chiller leak inspection records or maintenance logs?",
            options = listOf("A \$50 administrative parking ticket", "Criminal prosecution, substantial felony fines, and imprisonment under federal law, plus civil penalties exceeding \$59,000 per violation per day", "Mandatory cleaning of the EPA regional office", "Loss of a commercial driver's license for 30 days"),
            correctIndex = 1,
            explanation = "Knowingly falsifying environmental compliance records is a federal felony punishable by criminal fines and imprisonment under 18 U.S.C. § 1001.",
            citation = "Clean Air Act § 113(c) / 18 U.S.C. § 1001",
            isA2L = false
        ),
        Question(
            id = "t3-93",
            section = EPASection.TYPE_III,
            questionText = "What is the primary reason why low-pressure centrifugal chillers are designed with a single large evaporator barrel rather than multiple small evaporators?",
            options = listOf("Because large barrels look more impressive", "To minimize external piping joints and reduce potential air leakage entry points while providing massive shell volume for low-density vapor disengagement", "Because copper is cheaper than steel", "Because low-pressure refrigerants cannot flow through round pipes"),
            correctIndex = 1,
            explanation = "Minimizing gasketed joints reduces air infiltration into the vacuum shell, and large volume allows vapor to separate smoothly without entraining liquid droplets.",
            citation = "Chiller Structural Design Principles",
            isA2L = false
        ),
        Question(
            id = "t3-94",
            section = EPASection.TYPE_III,
            questionText = "What safety feature is required on mechanical room ventilation exhaust ducts under ASHRAE Standard 15?",
            options = listOf("The ducts must discharge inside the building lobby", "The exhaust duct must terminate outdoors away from any air intake and have no obstructions, and dampers must fail-safe to the OPEN position", "The duct must have a wood-burning fire damper that stays closed", "The duct must be made of combustible plastic"),
            correctIndex = 1,
            explanation = "Emergency exhaust must vent directly to safe outdoor dispersal points, and dampers must fail open to ensure ventilation during electrical failures.",
            citation = "ASHRAE Standard 15 § 8.11.5",
            isA2L = false
        ),
        Question(
            id = "t3-95",
            section = EPASection.TYPE_III,
            questionText = "What should a technician do if an eddy current test on a low-pressure chiller indicates that 10% of the condenser tubes have wall thinning exceeding 50%?",
            options = listOf("Ignore the test until the tubes leak water into the refrigerant", "Plug the damaged tubes with brass or vulcanized tapered plugs, or schedule a complete tube bundle retubing before putting the chiller back in service", "Fill the damaged tubes with expanding foam", "Pressurize the tubes to 500 psig to pop out the dents"),
            correctIndex = 1,
            explanation = "Tubes eroded past 50% wall thickness risk imminent bursting. They must be mechanically plugged or replaced to prevent catastrophic water contamination.",
            citation = "TEMA Standards / RSES Technical Guide",
            isA2L = false
        ),
        Question(
            id = "t3-96",
            section = EPASection.TYPE_III,
            questionText = "What is the maximum percentage of chiller heat exchanger tubes that can be plugged before overall chiller capacity and water pressure drop become unacceptable?",
            options = listOf("Up to 50% of tubes", "Typically no more than 5% to 10% of total tube count, as specified by the chiller manufacturer", "Exactly 1 tube only", "All tubes can be plugged"),
            correctIndex = 1,
            explanation = "Plugging more than 5% to 10% of tubes severely chokes water flow, elevates water pressure drop across heads, and degrades cooling capacity.",
            citation = "Trane Centravac Engineering Guidelines",
            isA2L = false
        ),
        Question(
            id = "t3-97",
            section = EPASection.TYPE_III,
            questionText = "What is the primary function of the economizer flash chamber on a multi-stage low-pressure centrifugal chiller?",
            options = listOf("To filter dirt from the condenser water", "To flash-cool liquid refrigerant between stages of compression, improving thermodynamic cycle efficiency and reducing compressor power consumption", "To heat building domestic hot water", "To pump air out of the evaporator"),
            correctIndex = 1,
            explanation = "Economizers flash a portion of liquid at intermediate pressure to subcool the remaining liquid, while flashed vapor enters the second stage impeller, boosting cycle efficiency.",
            citation = "ASHRAE Systems and Equipment Handbook",
            isA2L = false
        ),
        Question(
            id = "t3-98",
            section = EPASection.TYPE_III,
            questionText = "Under ASHRAE Standard 34, what criteria determines whether a low-pressure refrigerant is assigned to Group A or Group B?",
            options = listOf("Flammability burning velocity", "Toxicity: Group A refrigerants have an Occupational Exposure Limit (OEL) of 400 ppm or greater; Group B refrigerants have an OEL below 400 ppm", "The color of the storage drum", "Whether the chemical contains carbon"),
            correctIndex = 1,
            explanation = "Toxicity grouping is based on OSHA/ACGIH exposure limits: Class A denotes lower toxicity (>= 400 ppm), while Class B denotes higher toxicity (< 400 ppm).",
            citation = "ASHRAE Standard 34 § 6.1.1",
            isA2L = false
        ),
        Question(
            id = "t3-99",
            section = EPASection.TYPE_III,
            questionText = "What is the ultimate purpose of the EPA Section 608 Type III certification?",
            options = listOf("To allow technicians to service household refrigerators", "To certify that technicians have demonstrated technical and regulatory competence in the safe, legal, and environmentally sound handling of low-pressure appliances and centrifugal chillers", "To grant a license to operate steam locomotives", "To authorize technicians to work on car air conditioners"),
            correctIndex = 1,
            explanation = "Type III certification authorizes technicians to service, maintain, repair, and evacuate low-pressure appliances and centrifugal chillers under Clean Air Act rules.",
            citation = "40 CFR § 82.161",
            isA2L = false
        ),
        Question(
            id = "t3-100",
            section = EPASection.TYPE_III,
            questionText = "What is the single most important procedural discipline when recharging a low-pressure chiller from a deep vacuum?",
            options = listOf("Charge liquid refrigerant directly into the evaporator sight glass", "Maintain continuous water circulation in both the evaporator and condenser, vapor charge until saturation temperature is above 36°F (above 32°F freezing), and only then introduce liquid", "Turn off all water circulating pumps and electrical power", "Heat the recovery cylinder with an oxy-acetylene torch"),
            correctIndex = 1,
            explanation = "Running water pumps and charging vapor first until saturation temperature clears 36°F prevents flash boiling from freezing standing water in copper tubes, preventing catastrophic chiller destruction.",
            citation = "EPA 608 Type III Operational Excellence",
            isA2L = false
        )
    )
}
