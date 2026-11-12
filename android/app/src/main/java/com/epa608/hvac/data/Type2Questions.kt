package com.epa608.hvac.data

import com.epa608.hvac.domain.EPASection
import com.epa608.hvac.domain.Question

object Type2Questions {
    val list: List<Question> = listOf(
        Question(
            id = "t2-1",
            section = EPASection.TYPE_II,
            questionText = "Under 40 CFR § 82.157(c)(2), comfort cooling chillers and rooftop AC units containing 50 or more pounds of refrigerant must be repaired if the annual leak rate exceeds what percentage?",
            options = listOf("10%", "15%", "20%", "30%"),
            correctIndex = 0,
            explanation = "The statutory leak rate threshold for comfort cooling equipment containing 50+ pounds is 10%.",
            citation = "40 CFR § 82.157(c)(2)(iii)",
            isA2L = false
        ),
        Question(
            id = "t2-2",
            section = EPASection.TYPE_II,
            questionText = "How many days does an owner/operator have to repair a leak in an industrial process refrigeration system exceeding the 30% statutory threshold?",
            options = listOf("10 days", "30 days", "60 days", "90 days"),
            correctIndex = 1,
            explanation = "Under 40 CFR § 82.157(d), technicians have 30 days to repair systems that exceed the statutory leak threshold, unless an exemption or industrial shutdown extension applies.",
            citation = "40 CFR § 82.157(d)",
            isA2L = false
        ),
        Question(
            id = "t2-3",
            section = EPASection.TYPE_II,
            questionText = "Prior to recharging a high-pressure commercial system after completing leak repairs, what mandatory verification tests are required by EPA regulations?",
            options = listOf("Only an operational test after 30 days", "An Initial Verification Test before recharging, followed by a Follow-up Verification Test within 10 days of normal operation", "A visual oil inspection only", "No verification tests are required if electronic leak detectors were used"),
            correctIndex = 1,
            explanation = "40 CFR § 82.157 mandates two verification tests: an initial test conducted prior to full recharge, and a follow-up test conducted within 10 days of the appliance operating at normal operating characteristics.",
            citation = "40 CFR § 82.157(e)",
            isA2L = false
        ),
        Question(
            id = "t2-4",
            section = EPASection.TYPE_II,
            questionText = "Under 40 CFR § 82.156 Table 1, what vacuum level must be drawn when recovering refrigerant from a system containing 250 lbs of R-22 using post-1993 equipment?",
            options = listOf("0 psig", "4 inches of Hg vacuum", "10 inches of Hg vacuum", "15 inches of Hg vacuum"),
            correctIndex = 2,
            explanation = "Table 1 mandates 10 inches of Hg vacuum for appliances containing 200 or more pounds of high-pressure refrigerant serviced with recovery equipment manufactured on or after November 15, 1993.",
            citation = "40 CFR § 82.156 Table 1",
            isA2L = false
        ),
        Question(
            id = "t2-5",
            section = EPASection.TYPE_II,
            questionText = "Why must zeotropic refrigerant blends (such as R-407C or R-410A) always be charged into an HVAC system as a liquid rather than a vapor?",
            options = listOf("Liquid refrigerant is less corrosive to compressor windings", "Zeotropic blends fractionate, meaning different refrigerant components boil at different rates; vapor charging causes blend composition shift", "Vapor refrigerant cannot pass through a manifold gauge set", "Liquid charging reduces the risk of oil slugging in the compressor"),
            correctIndex = 1,
            explanation = "Zeotropic mixtures have temperature glide and fractionate. Charging as a vapor draws off the higher-pressure component first, altering the chemical composition of the remaining refrigerant.",
            citation = "ASHRAE Standard 34 / Section 608",
            isA2L = false
        ),
        Question(
            id = "t2-6",
            section = EPASection.TYPE_II,
            questionText = "Which of the following operations constitutes a 'major repair' under EPA Section 608 definitions (40 CFR § 82.152)?",
            options = listOf("Replacing a Schrader valve core", "Replacing a thermostatic expansion valve (TXV)", "Replacing the compressor, condenser coil, evaporator coil, or auxiliary heat exchanger", "Replacing a liquid line filter drier"),
            correctIndex = 2,
            explanation = "EPA defines a 'major repair' strictly as the removal or replacement of the compressor, condenser, evaporator coil, or auxiliary heat exchanger coil.",
            citation = "40 CFR § 82.152",
            isA2L = false
        ),
        Question(
            id = "t2-7",
            section = EPASection.TYPE_II,
            questionText = "Under 40 CFR § 82.156 Table 1, what evacuation level is required prior to opening a system containing 45 lbs of R-410A for a major repair using post-1993 recovery equipment?",
            options = listOf("0 psig (0 inches of Hg vacuum)", "4 inches of Hg vacuum", "10 inches of Hg vacuum", "15 inches of Hg vacuum"),
            correctIndex = 0,
            explanation = "Per Table 1, high-pressure appliances containing less than 200 pounds of refrigerant require an evacuation depth of 0 inches of Hg (0 psig) when using post-1993 equipment.",
            citation = "40 CFR § 82.156 Table 1",
            isA2L = false
        ),
        Question(
            id = "t2-8",
            section = EPASection.TYPE_II,
            questionText = "Under current EPA Section 608 rules (40 CFR § 82.157(c)(2)), what is the annual leak rate trigger threshold for Commercial Refrigeration systems containing 50 or more pounds of refrigerant?",
            options = listOf("10%", "20%", "30%", "35%"),
            correctIndex = 1,
            explanation = "Under 40 CFR § 82.157(c)(2)(ii), the threshold for commercial refrigeration containing 50+ lbs is 20%. Comfort cooling is 10%, and industrial process refrigeration is 30%.",
            citation = "40 CFR § 82.157(c)(2)(ii)",
            isA2L = false
        ),
        Question(
            id = "t2-9",
            section = EPASection.TYPE_II,
            questionText = "Under current EPA Section 608 rules, what is the statutory leak rate trigger threshold for Industrial Process Refrigeration (IPR) systems containing 50 or more pounds of refrigerant?",
            options = listOf("10%", "20%", "30%", "50%"),
            correctIndex = 2,
            explanation = "Under 40 CFR § 82.157(c)(2)(i), industrial process refrigeration equipment containing 50 or more pounds has a 30% leak rate threshold.",
            citation = "40 CFR § 82.157(c)(2)(i)",
            isA2L = false
        ),
        Question(
            id = "t2-10",
            section = EPASection.TYPE_II,
            questionText = "If an equipment owner fails to successfully repair a leak in a commercial refrigeration rack containing 1,200 lbs of R-404A within 30 days, what must be developed?",
            options = listOf("A fine payment receipt to the city council", "A certified 1-year retrofit or retirement plan signed by the owner to replace or convert the equipment to an exempt or compliant refrigerant", "An evacuation plan to vent all remaining refrigerant immediately", "A letter requesting an automatic 5-year waiver from the EPA"),
            correctIndex = 1,
            explanation = "If the 30-day repair window passes without successful repair, the owner must formulate an official 1-year retrofit or retirement plan under 40 CFR § 82.157(h).",
            citation = "40 CFR § 82.157(h)",
            isA2L = false
        ),
        Question(
            id = "t2-11",
            section = EPASection.TYPE_II,
            questionText = "How long do commercial equipment owners have to execute an approved retrofit or retirement plan for an appliance that could not be repaired within 30 days?",
            options = listOf("6 months", "1 year (12 months)", "2 years", "5 years"),
            correctIndex = 1,
            explanation = "The retrofit or retirement plan must be completed within 1 year of its creation date, unless an official extension applies.",
            citation = "40 CFR § 82.157(h)",
            isA2L = false
        ),
        Question(
            id = "t2-12",
            section = EPASection.TYPE_II,
            questionText = "When can an industrial process refrigeration facility qualify for an extension beyond the standard 30-day leak repair timeline?",
            options = listOf("Whenever the weather is rainy", "If an extension is requested because the repair requires an industrial facility shutdown that must be scheduled, or if replacement parts are unavailable with proof of backorder", "If the technician forgets to order parts", "Extensions are never granted under any circumstance"),
            correctIndex = 1,
            explanation = "Under 40 CFR § 82.157(f), extensions are permitted if a full facility shutdown is required to execute repairs or if necessary replacement parts are on backorder.",
            citation = "40 CFR § 82.157(f)",
            isA2L = false
        ),
        Question(
            id = "t2-13",
            section = EPASection.TYPE_II,
            questionText = "What calculation method may be used under 40 CFR § 82.157 to determine whether an appliance has exceeded its statutory leak rate threshold?",
            options = listOf("The Annualizing Method or the Rolling Average Method", "The Square Footage Method", "The Ambient Humidity Calculation", "The Compression Ratio Method"),
            correctIndex = 0,
            explanation = "EPA regulations approve two leak calculation methods: the Annualizing Method (extrapolating from days between charges) and the Rolling Average Method (looking at 365 days of additions).",
            citation = "40 CFR § 82.157(b)",
            isA2L = false
        ),
        Question(
            id = "t2-14",
            section = EPASection.TYPE_II,
            questionText = "If 15 pounds of R-410A are added to a comfort cooling rooftop unit with a 60-pound full nameplate charge 90 days after the last charge, what is the annualized leak rate?",
            options = listOf("25%", "60%", "101.4%", "10.0%"),
            correctIndex = 2,
            explanation = "Annualized Rate = (Pounds added / Full Charge) × (365 / Days since last addition) × 100% = (15 / 60) × (365 / 90) × 100% = 0.25 × 4.055 × 100% = 101.4%, far exceeding the 10% threshold.",
            citation = "40 CFR § 82.157(b)(1)",
            isA2L = false
        ),
        Question(
            id = "t2-15",
            section = EPASection.TYPE_II,
            questionText = "Under 40 CFR § 82.157(l), for how many years must owners of high-pressure systems with 50 or more pounds of refrigerant keep leak inspection and servicing records?",
            options = listOf("1 year", "3 years", "5 years", "10 years"),
            correctIndex = 1,
            explanation = "Owners/operators must keep records of all service orders, refrigerant additions, leak calculations, and verification tests on systems with 50+ lbs for at least three (3) years.",
            citation = "40 CFR § 82.157(l)",
            isA2L = false
        ),
        Question(
            id = "t2-16",
            section = EPASection.TYPE_II,
            questionText = "When is a comfort cooling system containing 50 or more pounds of refrigerant required to undergo periodic leak inspections after reaching the 10% leak threshold?",
            options = listOf("Once every month until the unit is scrapped", "Once every calendar year until the owner demonstrates that the leak rate has not exceeded 10% for four consecutive quarters", "Every 10 years", "Only when an EPA inspector visits in person"),
            correctIndex = 1,
            explanation = "Under 40 CFR § 82.157(g), comfort cooling appliances that exceeded the threshold must be inspected once every calendar year until verified below threshold for a full year.",
            citation = "40 CFR § 82.157(g)(1)",
            isA2L = false
        ),
        Question(
            id = "t2-17",
            section = EPASection.TYPE_II,
            questionText = "When is a commercial refrigeration system containing 500 or more pounds of refrigerant required to undergo periodic leak inspections after exceeding the 20% threshold?",
            options = listOf("Once every 3 months (quarterly) until demonstrated below the threshold for four consecutive quarters", "Once every week", "Every 2 years", "Inspections are never required if an automatic alarm is installed"),
            correctIndex = 0,
            explanation = "Commercial refrigeration equipment containing 500+ lbs of refrigerant must be inspected quarterly until verified below the leak rate for four consecutive quarters.",
            citation = "40 CFR § 82.157(g)(2)",
            isA2L = false
        ),
        Question(
            id = "t2-18",
            section = EPASection.TYPE_II,
            questionText = "What is an acceptable alternative to quarterly manual leak inspections for commercial refrigeration systems containing 500 or more pounds of refrigerant?",
            options = listOf("Installing an EPA-certified continuously operating automated leak detection system that alerts when concentrations exceed thresholds and is audited annually", "Turning the refrigeration rack off for 12 hours a day", "Using only soapy water once a year", "Painting all copper lines red"),
            correctIndex = 0,
            explanation = "Owners may install continuous automated leak detection (ALD) systems monitored 24/7 in lieu of manual quarterly inspections, provided the system is audited annually.",
            citation = "40 CFR § 82.157(g)(4)",
            isA2L = false
        ),
        Question(
            id = "t2-19",
            section = EPASection.TYPE_II,
            questionText = "When using post-1993 recovery equipment to recover refrigerant from a 15-ton split AC system containing 35 lbs of R-410A where an active leak prevents pulling a vacuum, what must be done?",
            options = listOf("The technician must vent the remaining 35 lbs into the air", "The technician must evacuate the system down to atmospheric pressure (0 psig) before opening for service", "The technician must pressurize with 500 psig nitrogen while recovering", "The technician must pour motor oil into the recovery line"),
            correctIndex = 1,
            explanation = "Under 40 CFR § 82.156(a)(3), if leaks prevent drawing the statutory vacuum without pulling ambient air into the cylinder, evacuate to 0 psig before opening.",
            citation = "40 CFR § 82.156(a)(3)",
            isA2L = false
        ),
        Question(
            id = "t2-20",
            section = EPASection.TYPE_II,
            questionText = "What is the primary operational hazard of recovering liquid refrigerant from the receiver of a large commercial system too rapidly?",
            options = listOf("The recovery cylinder will overheat", "The sudden pressure drop causes rapid flash boiling of liquid inside the receiver, which drops temperatures and can freeze water coils or cause severe cylinder liquid foaming", "The compressor motor will reverse polarity", "The copper piping will corrode instantly"),
            correctIndex = 1,
            explanation = "Rapid evacuation causes flashing and extreme cooling. In water-cooled equipment, rapid boiling can freeze and rupture chiller or condenser water tubes.",
            citation = "RSES High-Pressure Service Manual",
            isA2L = false
        ),
        Question(
            id = "t2-21",
            section = EPASection.TYPE_II,
            questionText = "Why should a technician always recover liquid refrigerant first from a system before recovering vapor?",
            options = listOf("Liquid contains more oxygen than vapor", "Recovering liquid first removes the vast majority of the refrigerant mass much faster than pumping vapor", "Vapor cannot be legally stored in recovery cylinders", "Liquid recovery increases the vacuum pump oil level"),
            correctIndex = 1,
            explanation = "Liquids are hundreds of times denser than vapor; recovering liquid first extracts ~80-90% of system mass in minutes, leaving only vapor for final evacuation.",
            citation = "AHRI Standard 740 Guidelines",
            isA2L = false
        ),
        Question(
            id = "t2-22",
            section = EPASection.TYPE_II,
            questionText = "Where is the liquid refrigerant typically recovered from on a commercial high-pressure system equipped with service valves?",
            options = listOf("From the suction line service port on the compressor", "From the king valve (liquid line service valve) located on the outlet of the receiver", "From the top of the oil separator", "From the crankcase oil drain plug"),
            correctIndex = 1,
            explanation = "The liquid line service valve (king valve) at the bottom of the receiver provides direct access to high-density subcooled liquid refrigerant.",
            citation = "HVAC Commercial Systems Handbook",
            isA2L = false
        ),
        Question(
            id = "t2-23",
            section = EPASection.TYPE_II,
            questionText = "What method can be used to accelerate the recovery of remaining vapor from a high-pressure commercial system after liquid has been removed?",
            options = listOf("Heating the system components with warm air or electric heating blankets, and chilling the recovery cylinder in ice water", "Pressurizing the system with 2,000 psig oxygen", "Running the system compressor without any oil", "Pouring liquid nitrogen over the recovery machine"),
            correctIndex = 0,
            explanation = "Warming the evaporator/condenser elevates system vapor pressure while chilling the recovery tank lowers receiving pressure, dramatically boosting transfer rate.",
            citation = "AHRI Recovery Practice Bulletin",
            isA2L = false
        ),
        Question(
            id = "t2-24",
            section = EPASection.TYPE_II,
            questionText = "What is the function of a crankcase pressure regulating (CPR) valve installed on a low-temperature commercial refrigeration system?",
            options = listOf("To keep the head pressure above 300 psig during cold winter nights", "To limit suction pressure entering the compressor during defrost recovery or startup, protecting the compressor motor from electrical overload", "To inject liquid refrigerant into the oil sump", "To vent excess refrigerant to the atmosphere"),
            correctIndex = 1,
            explanation = "During warm startup or defrost termination, high evaporator suction pressure would overload the motor. The CPR valve throttles suction pressure to a safe maximum RLA.",
            citation = "Sporlan Valve Application Bulletin",
            isA2L = false
        ),
        Question(
            id = "t2-25",
            section = EPASection.TYPE_II,
            questionText = "What is the function of an evaporator pressure regulating (EPR) valve in a multi-evaporator supermarket refrigeration rack?",
            options = listOf("To maintain a minimum evaporator pressure and temperature in warmer walk-in boxes connected to a common lower-temperature suction header", "To pump oil back to the compressor", "To prevent water lines from freezing in winter", "To shut off the condenser fans"),
            correctIndex = 0,
            explanation = "EPR valves maintain different evaporating temperatures (e.g. 35°F for meat cases, 45°F for produce) on separate evaporators sharing a single suction compressor group.",
            citation = "Sporlan Valve Engineering Guide",
            isA2L = false
        ),
        Question(
            id = "t2-26",
            section = EPASection.TYPE_II,
            questionText = "When charging a zeotropic blend like R-407C into a high-pressure commercial system that is completely empty and under deep vacuum, how should the initial charge be introduced?",
            options = listOf("As a vapor into the compressor suction service port", "As a liquid directly into the high-side liquid line service valve while the system is turned OFF", "By pouring liquid into the crankcase oil sight glass", "Through an open hose connected to the roof drain"),
            correctIndex = 1,
            explanation = "Liquid is charged into the high side with the compressor off to take advantage of the vacuum, preventing blend fractionation and protecting compressor valves from liquid slugging.",
            citation = "EPA 608 Type II Best Practices",
            isA2L = false
        ),
        Question(
            id = "t2-27",
            section = EPASection.TYPE_II,
            questionText = "If additional zeotropic refrigerant (such as R-454B or R-410A) must be added to an already operating commercial system, how must it be added?",
            options = listOf("As a vapor directly into the high side discharge line", "As a liquid through a throttling metering valve into the low-side suction service valve while the compressor is running", "By heating the cylinder with an acetylene torch until pressure hits 600 psig", "By injecting liquid directly into the crankcase"),
            correctIndex = 1,
            explanation = "Liquid must be drawn from the cylinder to maintain blend composition, but throttled to flash into vapor before entering the compressor suction to avoid mechanical slugging.",
            citation = "RSES Refrigerant Charging Protocols",
            isA2L = true
        ),
        Question(
            id = "t2-28",
            section = EPASection.TYPE_II,
            questionText = "Under the 2026/2027 EPA AIM Act Technology Transitions rule (40 CFR Part 84), what GWP limit applies to newly manufactured commercial supermarket refrigeration systems with charges exceeding 200 lbs?",
            options = listOf("150 GWP", "300 GWP", "1,500 GWP", "2,500 GWP"),
            correctIndex = 0,
            explanation = "Under 40 CFR Part 84 Subpart B, large commercial supermarket systems with charges over 200 lbs have a GWP limit of 150 (or 300 for certain rack configurations), phasing out R-404A/R-507.",
            citation = "40 CFR § 84.54",
            isA2L = false
        ),
        Question(
            id = "t2-29",
            section = EPASection.TYPE_II,
            questionText = "What replacement refrigerants are primarily being adopted to meet the < 150 GWP mandate for new commercial supermarket refrigeration racks?",
            options = listOf("R-22 and CFC-12", "Carbon Dioxide (R-744) transcritical systems and low-GWP A2L/HFO blends", "Pure sulfur dioxide", "R-134a with mineral oil"),
            correctIndex = 1,
            explanation = "Supermarkets are adopting transcritical CO2 (R-744, GWP=1) systems and low-GWP A2L/A1 HFO blends to comply with AIM Act GWP ceilings.",
            citation = "EPA Technology Transitions Guidance",
            isA2L = true
        ),
        Question(
            id = "t2-30",
            section = EPASection.TYPE_II,
            questionText = "What is the typical operating discharge pressure range of a transcritical CO2 (R-744) commercial refrigeration system on a hot summer day?",
            options = listOf("100 to 200 psig", "400 to 600 psig", "1,200 to 1,500+ psig (80 to 105+ bar)", "5,000 psig"),
            correctIndex = 2,
            explanation = "Because CO2 has a low critical temperature (87.8°F), hot summer days force operation in the transcritical supercritical gas-cooler regime at pressures exceeding 1,200 to 1,500 psig.",
            citation = "ASHRAE Refrigeration Applications / R-744 Manual",
            isA2L = false
        ),
        Question(
            id = "t2-31",
            section = EPASection.TYPE_II,
            questionText = "Why must technicians servicing R-744 (CO2) systems use high-pressure rated manifolds and stainless steel or reinforced thermoplastic hoses?",
            options = listOf("Because standard copper lines will melt from heat", "Because standard 1/4\" rubber refrigeration hoses have working pressure ratings (typically 800 psig) far below the 1,500+ psig operating pressures of CO2 circuits", "Because CO2 dissolves brass valves within 5 minutes", "Because standard hoses make CO2 turn into carbon soot"),
            correctIndex = 1,
            explanation = "Standard manifold hoses burst at high pressures; CO2 servicing requires specialized gauges and hoses rated for at least 2,000 to 2,500 psig working pressure.",
            citation = "ASHRAE Standard 15 / ASME B31.5",
            isA2L = false
        ),
        Question(
            id = "t2-32",
            section = EPASection.TYPE_II,
            questionText = "What dangerous phenomenon occurs when liquid CO2 (R-744) is trapped inside a closed section of refrigeration piping with no pressure relief protection?",
            options = listOf("The CO2 turns into dry ice and permanently freezes the pipe", "Extremely high hydraulic expansion occurs as temperature rises, generating pressures exceeding 3,000+ psig that can violently rupture copper piping or fittings", "The piping collapses from intense internal vacuum", "The oil inside catches fire spontaneously"),
            correctIndex = 1,
            explanation = "Liquid CO2 expands dramatically when trapped without a vapor cushion. Every isolated piping section must have an auxiliary pressure relief valve to vent hydraulic overpressure.",
            citation = "IIAR / ASHRAE Safety Directives",
            isA2L = false
        ),
        Question(
            id = "t2-33",
            section = EPASection.TYPE_II,
            questionText = "What occurs if liquid CO2 (R-744) is rapidly charged into an HVAC system that is in a deep vacuum below the CO2 triple point pressure (75.1 psia / 60.4 psig)?",
            options = listOf("The liquid freezes instantly into solid dry ice snow inside the tubing, blocking lines and creating hazardous pressure differentials upon subsequent expansion", "The vacuum pump motor catches fire", "The system will cool to -200°F and never start", "The compressor oil separates into clean gasoline"),
            correctIndex = 0,
            explanation = "Below 60.4 psig, CO2 cannot exist as a liquid. Liquid flash-evaporates below the triple point, converting immediately into solid dry ice snow that chokes expansion valves and fittings.",
            citation = "R-744 Service Practice Guidelines",
            isA2L = false
        ),
        Question(
            id = "t2-34",
            section = EPASection.TYPE_II,
            questionText = "To what pressure must an empty system be pressurized with CO2 vapor before introducing liquid CO2 into the refrigeration circuit?",
            options = listOf("10 psig", "At least 75 to 80 psig (above the 60.4 psig triple point)", "500 psig", "No vapor prepressurization is needed"),
            correctIndex = 1,
            explanation = "Technicians must vapor-charge CO2 until system pressure exceeds 80 psig to guarantee that subsequent liquid charging remains safely in the liquid phase without dry ice solidification.",
            citation = "Emerson Climate Technologies CO2 Application Guide",
            isA2L = false
        ),
        Question(
            id = "t2-35",
            section = EPASection.TYPE_II,
            questionText = "What is the primary function of a suction line accumulator installed upstream of a commercial compressor?",
            options = listOf("To store liquid refrigerant during winter shutdowns", "To trap liquid refrigerant and oil returning from the evaporator, preventing liquid from directly entering the compressor suction cylinders and causing valve slugging", "To filter acid from the discharge gas", "To boost suction pressure by 20 psig"),
            correctIndex = 1,
            explanation = "Accumulators provide a vapor trap with a tiny metering orifice at the bottom of the internal U-tube to meter return oil safely back to the compressor without liquid slugging.",
            citation = "Sporlan Accumulator Technical Sheet",
            isA2L = false
        ),
        Question(
            id = "t2-36",
            section = EPASection.TYPE_II,
            questionText = "What is the purpose of the tiny bleed hole (metering orifice) located in the bottom of a suction line accumulator's internal U-tube?",
            options = listOf("To drain liquid refrigerant onto the floor", "To allow trapped lubricating oil and a metered trickle of liquid to return safely to the compressor crankcase", "To equalize atmospheric pressure with the evaporator", "To allow technicians to insert a thermocouple probe"),
            correctIndex = 1,
            explanation = "The bleed hole ensures oil does not pool indefinitely in the accumulator; it meters oil along with a small amount of atomized vapor back to the compressor.",
            citation = "ACCA Commercial Refrigeration Manual",
            isA2L = false
        ),
        Question(
            id = "t2-37",
            section = EPASection.TYPE_II,
            questionText = "What is the primary function of an oil separator installed in the discharge line of a commercial refrigeration compressor?",
            options = listOf("To cool the discharge gas before it enters the condenser", "To separate atomized lubricating oil from hot discharge gas and automatically return it to the compressor crankcase before it enters the condenser and coils", "To remove moisture from liquid refrigerant", "To prevent high head pressure during hot weather"),
            correctIndex = 1,
            explanation = "Oil separators reduce oil circulation through the condenser and evaporator, improving heat transfer and maintaining crankcase oil levels.",
            citation = "Temprite Oil Separator Application Guide",
            isA2L = false
        ),
        Question(
            id = "t2-38",
            section = EPASection.TYPE_II,
            questionText = "Where is the oil separator physically located in a commercial refrigeration system?",
            options = listOf("In the suction line between evaporator and accumulator", "In the high-pressure discharge line between the compressor discharge service valve and the inlet of the condenser", "In the liquid line after the filter-drier", "Inside the expansion valve bulb"),
            correctIndex = 1,
            explanation = "Discharge oil separators are piped immediately downstream of the compressor discharge to strip oil from the hot gas stream before it reaches heat exchangers.",
            citation = "ASHRAE Equipment Handbook",
            isA2L = false
        ),
        Question(
            id = "t2-39",
            section = EPASection.TYPE_II,
            questionText = "Under what condition will an oil separator fail to return oil to the compressor crankcase?",
            options = listOf("When the condenser fan runs on low speed", "If the internal float valve becomes stuck closed with sludge or carbon debris, or if the oil return line screen becomes plugged", "If the ambient temperature is exactly 70°F", "Whenever POE oil is used instead of mineral oil"),
            correctIndex = 1,
            explanation = "A jammed float or plugged internal needle valve prevents separated oil from draining back to the crankcase, causing compressor oil starvation.",
            citation = "RSES Technical Guide",
            isA2L = false
        ),
        Question(
            id = "t2-40",
            section = EPASection.TYPE_II,
            questionText = "What symptom indicates that an oil separator's internal needle float valve is stuck permanently in the OPEN position?",
            options = listOf("The compressor crankcase runs completely dry", "Hot discharge gas blows continuously through the oil return line into the compressor crankcase, elevating crankcase pressure and temperature", "The condenser coil freezes over with solid frost", "The suction pressure drops into a 29-inch vacuum"),
            correctIndex = 1,
            explanation = "A stuck-open float bypasses high-pressure discharge vapor directly into the low-side crankcase, causing high crankcase temperature and loss of pumping capacity.",
            citation = "Copeland Commercial Application Guide",
            isA2L = false
        ),
        Question(
            id = "t2-41",
            section = EPASection.TYPE_II,
            questionText = "Why must a moisture indicator sight glass be inspected only when the system is operating at full load with a clear liquid line?",
            options = listOf("Because sight glasses are invisible when the system is off", "Because bubbles in the sight glass during startup or low load can be normal, but bubbles at steady full load indicate flashing caused by low refrigerant charge or excessive restriction", "Because moisture only exists when the compressor runs at 3,500 RPM", "Because the glass will shatter if inspected while dormant"),
            correctIndex = 1,
            explanation = "A clear column of liquid at full load verifies adequate subcooling. Bubbles indicate flashing from undercharge or a clogged filter-drier creating pressure drop.",
            citation = "Sporlan Sight Glass Guidelines",
            isA2L = false
        ),
        Question(
            id = "t2-42",
            section = EPASection.TYPE_II,
            questionText = "What does a color change from green (or dry) to yellow (or wet) on a liquid line moisture indicator sight glass indicate?",
            options = listOf("The system contains excessive mineral oil", "The moisture level in the circulating refrigerant has exceeded safe thresholds, requiring replacement of the liquid line filter-drier", "The refrigerant has turned into sulfur", "The sight glass is cracked and leaking"),
            correctIndex = 1,
            explanation = "Moisture indicators contain cobalt salts that change color when relative moisture exceeds critical limits (~50-100 ppm), signaling the filter-drier is saturated.",
            citation = "Emerson Moisture Indicator Manual",
            isA2L = false
        ),
        Question(
            id = "t2-43",
            section = EPASection.TYPE_II,
            questionText = "What is the typical pressure drop across a clean liquid line filter-drier in an operating commercial refrigeration system?",
            options = listOf("10 to 20 psig", "Less than 1 to 2 psig at design operating flow rate", "50 psig", "Zero psig under all conditions"),
            correctIndex = 1,
            explanation = "A healthy filter-drier has minimal flow restriction (< 2 psig drop). A temperature drop or frost across the drier body indicates internal plugging.",
            citation = "ASHRAE Systems Handbook",
            isA2L = false
        ),
        Question(
            id = "t2-44",
            section = EPASection.TYPE_II,
            questionText = "If there is a measurable temperature difference of 5°F or more between the inlet and outlet copper piping of a liquid line filter-drier, what does this indicate?",
            options = listOf("The filter-drier is performing exceptional subcooling", "The filter-drier core is clogged with debris or desiccant sludge, causing an internal pressure drop that causes refrigerant to flash-boil and cool", "The compressor is running backward", "The condenser fan has failed"),
            correctIndex = 1,
            explanation = "A temperature drop across a filter-drier is a definitive indicator of internal blockage; pressure drops across the restriction, triggering flash refrigeration.",
            citation = "ACCA Commercial Troubleshooting",
            isA2L = false
        ),
        Question(
            id = "t2-45",
            section = EPASection.TYPE_II,
            questionText = "What type of expansion device uses an external equalization line connected to the suction line immediately downstream of the sensing bulb?",
            options = listOf("An automatic expansion valve (AXV)", "A thermostatic expansion valve (TXV) on evaporator coils with high internal refrigerant pressure drop or multi-circuit refrigerant distributors", "A fixed capillary tube", "A float valve"),
            correctIndex = 1,
            explanation = "External equalizers compensate for pressure drops through multi-circuit distributors and large evaporator coils, ensuring the valve maintains correct superheat.",
            citation = "Sporlan Valve Bulletin 10-10",
            isA2L = false
        ),
        Question(
            id = "t2-46",
            section = EPASection.TYPE_II,
            questionText = "What happens if a technician forgets to connect the external equalizer line on a TXV and leaves the equalizer port open to the atmosphere?",
            options = listOf("The system runs at 100% capacity", "Refrigerant will continuously vent out of the open equalizer fitting into the atmosphere, violating Clean Air Act Section 608", "The valve will freeze shut and never open", "The sensing bulb will burst"),
            correctIndex = 1,
            explanation = "The equalizer port communicates directly with the underside of the TXV diaphragm. Leaving it open discharges refrigerant directly to the air.",
            citation = "40 CFR § 82.154 / Sporlan Valve Guide",
            isA2L = false
        ),
        Question(
            id = "t2-47",
            section = EPASection.TYPE_II,
            questionText = "Where should the thermostatic expansion valve (TXV) sensing bulb be physically mounted on a horizontal suction line with a diameter of 7/8\" or larger?",
            options = listOf("At the very bottom of the pipe (6 o'clock position)", "At the 4 o'clock or 8 o'clock position (approximately 45 degrees below horizontal) to avoid false readings from pooled oil in the bottom of the tube", "At the very top of the pipe (12 o'clock position)", "Mounted inside the compressor crankcase"),
            correctIndex = 1,
            explanation = "Oil pools at the 6 o'clock position and acts as a thermal insulator. Mounting at 4 or 8 o'clock ensures the bulb senses true vapor temperature.",
            citation = "Sporlan Valve Application Guide",
            isA2L = false
        ),
        Question(
            id = "t2-48",
            section = EPASection.TYPE_II,
            questionText = "Why must the TXV sensing bulb always be thoroughly insulated with waterproof foam insulation tape on an HVAC system?",
            options = listOf("To prevent the copper bulb from turning green", "To isolate the bulb from ambient air temperature so it responds strictly to suction vapor superheat rather than warmer room air", "To keep the bulb warm in winter", "To prevent electromagnetic interference"),
            correctIndex = 1,
            explanation = "Uninsulated bulbs absorb heat from surrounding ambient air, causing the TXV to open too far and over-flood the evaporator with liquid refrigerant.",
            citation = "RSES Service Guidelines",
            isA2L = false
        ),
        Question(
            id = "t2-49",
            section = EPASection.TYPE_II,
            questionText = "What is the primary indicator of a TXV whose sensing bulb has lost its internal thermodynamic vapor charge due to a capillary fracture?",
            options = listOf("The valve floods the evaporator with liquid", "The valve fails completely closed; suction pressure drops into a deep vacuum and the evaporator starves for refrigerant", "The head pressure climbs to 500 psig", "The compressor motor speeds up to 10,000 RPM"),
            correctIndex = 1,
            explanation = "Bulb charge pressure provides the opening force against spring pressure and evaporator pressure. When charge is lost, the closing spring forces the valve shut.",
            citation = "Sporlan Troubleshooting Guide",
            isA2L = false
        ),
        Question(
            id = "t2-50",
            section = EPASection.TYPE_II,
            questionText = "What is the function of a hot-gas bypass valve installed in a commercial refrigeration system?",
            options = listOf("To defrost the outdoor condenser", "To divert hot discharge vapor into the low side (evaporator inlet or suction line) during periods of low cooling load to prevent compressor short-cycling and freeze-up", "To dump excess refrigerant into the atmosphere", "To increase subcooling at the expansion valve"),
            correctIndex = 1,
            explanation = "Hot-gas bypass false-loads the compressor during low-demand periods, keeping suction pressure above freezing and preventing rapid on/off cycling.",
            citation = "Sporlan Bulletin 90-40",
            isA2L = false
        ),
        Question(
            id = "t2-51",
            section = EPASection.TYPE_II,
            questionText = "Where should hot-gas bypass discharge vapor be introduced when piped into the suction line ahead of the compressor?",
            options = listOf("Directly into the compressor oil sight glass", "Through a liquid-injection desuperheating thermostatic expansion valve (quench valve) to mix with liquid and prevent overheating the compressor suction", "Directly against the motor terminals", "Into the bottom of the accumulator"),
            correctIndex = 1,
            explanation = "Discharge gas is extremely hot. Bypassing directly into the suction requires a desuperheating valve to inject atomized liquid and prevent motor thermal tripping.",
            citation = "ASHRAE Systems Handbook",
            isA2L = false
        ),
        Question(
            id = "t2-52",
            section = EPASection.TYPE_II,
            questionText = "Under 40 CFR § 82.156 Table 1, what evacuation level is required for an appliance containing 350 lbs of R-410A using recovery equipment manufactured BEFORE November 15, 1993?",
            options = listOf("0 psig", "4 inches of Hg vacuum", "10 inches of Hg vacuum", "15 inches of Hg vacuum"),
            correctIndex = 1,
            explanation = "Pre-1993 equipment was less efficient; Table 1 mandates 4 inches of Hg vacuum for systems containing 200+ lbs serviced with pre-Nov 15, 1993 equipment.",
            citation = "40 CFR § 82.156 Table 1",
            isA2L = false
        ),
        Question(
            id = "t2-53",
            section = EPASection.TYPE_II,
            questionText = "Under 40 CFR § 82.156 Table 1, what evacuation depth is required for an appliance containing 50 lbs of high-pressure refrigerant serviced with pre-1993 equipment?",
            options = listOf("0 psig (0 inches of Hg vacuum)", "4 inches of Hg vacuum", "10 inches of Hg vacuum", "25 mm Hg absolute"),
            correctIndex = 0,
            explanation = "Table 1 specifies 0 inches of Hg (0 psig) for high-pressure systems with less than 200 lbs of refrigerant using pre-1993 equipment.",
            citation = "40 CFR § 82.156 Table 1",
            isA2L = false
        ),
        Question(
            id = "t2-54",
            section = EPASection.TYPE_II,
            questionText = "What is the primary indicator of an inoperative crankcase heater on an outdoor commercial condensing unit during cold weather?",
            options = listOf("The condenser fan runs continuously", "The compressor crankcase is cold to the touch and the oil sight glass shows severe foaming and high liquid level upon compressor startup", "The high-pressure cut-out switch trips immediately", "The liquid line filter-drier gets hot"),
            correctIndex = 1,
            explanation = "A failed crankcase heater allows refrigerant to migrate and condense in cold oil. At startup, the sudden pressure drop causes violent boiling and foaming.",
            citation = "Copeland Application Guidelines",
            isA2L = false
        ),
        Question(
            id = "t2-55",
            section = EPASection.TYPE_II,
            questionText = "What is the purpose of an outdoor ambient head pressure control valve (such as an ORI/ORD or floodback valve) on a commercial refrigeration system?",
            options = listOf("To maintain adequate high-side liquid pressure during cold winter weather by flooding the condenser with liquid refrigerant", "To turn off the compressor during rain", "To increase evaporator airflow", "To pump refrigerant out of the receiver"),
            correctIndex = 0,
            explanation = "During cold winter weather, excessive condenser cooling drops head pressure so low that expansion valves cannot feed sufficient liquid; flooding valves maintain head pressure.",
            citation = "Sporlan Head Pressure Bulletin",
            isA2L = false
        ),
        Question(
            id = "t2-56",
            section = EPASection.TYPE_II,
            questionText = "When retrofitting a commercial walk-in cooler from HCFC-22 to an HFC blend such as R-407C or R-448A, what must be done with the mineral oil?",
            options = listOf("The mineral oil can be left in the system untouched", "At least 95% of the mineral oil must be flushed and replaced with synthetic polyolester (POE) oil to ensure proper oil return", "The mineral oil must be diluted with water", "The oil should be burned off with a torch"),
            correctIndex = 1,
            explanation = "HFC and HFO blends do not mix with mineral oil. Leaving mineral oil in the circuit results in oil logging in the evaporator and catastrophic compressor failure.",
            citation = "RSES Retrofit Protocols",
            isA2L = false
        ),
        Question(
            id = "t2-57",
            section = EPASection.TYPE_II,
            questionText = "What is the typical maximum pressure differential across an operating suction line filter-drier before it must be replaced after a compressor burnout cleanup?",
            options = listOf("1 to 2 psig for air conditioning; 0.5 to 1 psig for low-temperature commercial refrigeration", "15 psig", "30 psig", "50 psig"),
            correctIndex = 0,
            explanation = "Suction filters cause suction pressure drop, reducing cooling capacity and elevating compression ratios. Driers must be replaced when drop exceeds 1 to 2 psig.",
            citation = "AHRI Service Bulletin",
            isA2L = false
        ),
        Question(
            id = "t2-58",
            section = EPASection.TYPE_II,
            questionText = "Why should technicians NEVER open a hermetic compressor's electrical junction box while the unit is energized under abnormal high pressure?",
            options = listOf("The cover will burn the technician's fingers", "A compromised terminal seal can blow out with explosive force, causing electrical arcing and spraying burning oil directly into the technician's face", "The wires will lose their copper coating", "The fan will speed up"),
            correctIndex = 1,
            explanation = "Terminal post blowouts have caused fatal burns and injuries. Always de-energize and verify pressure before inspecting damaged electrical terminal connections.",
            citation = "OSHA Fatal Fact Bulletin No. 42",
            isA2L = false
        ),
        Question(
            id = "t2-59",
            section = EPASection.TYPE_II,
            questionText = "What safety classification does refrigerant R-454B hold under ASHRAE Standard 34?",
            options = listOf("A1", "A2L", "A3", "B2L"),
            correctIndex = 1,
            explanation = "R-454B (Opteon XL41) is classified as Class A2L: lower toxicity (A) and mildly flammable (2L) with burning velocity <= 10 cm/s.",
            citation = "ASHRAE Standard 34-2022",
            isA2L = true
        ),
        Question(
            id = "t2-60",
            section = EPASection.TYPE_II,
            questionText = "What safety classification does refrigerant R-32 hold under ASHRAE Standard 34?",
            options = listOf("A1", "A2L", "A3", "B1"),
            correctIndex = 1,
            explanation = "Pure difluoromethane (R-32) is classified as Class A2L: non-toxic but mildly flammable with low burning velocity.",
            citation = "ASHRAE Standard 34-2022",
            isA2L = true
        ),
        Question(
            id = "t2-61",
            section = EPASection.TYPE_II,
            questionText = "Under UL 60335-2-40, what safety feature is mandatory on new residential split systems using A2L refrigerants like R-454B or R-32 when charge exceeds release limits?",
            options = listOf("A chemical foam fire sprinkler in the attic", "A factory-calibrated Refrigerant Detection System (RDS) sensor inside the indoor air handler that energizes continuous indoor fan circulation upon leak detection", "An automatic outdoor venting valve", "A manual pull station in every bedroom"),
            correctIndex = 1,
            explanation = "UL 60335-2-40 mandates factory RDS sensors that detect leaks and immediately start the indoor blower fan to dilute refrigerant concentrations far below the LFL.",
            citation = "UL Standard 60335-2-40 Edition 4",
            isA2L = true
        ),
        Question(
            id = "t2-62",
            section = EPASection.TYPE_II,
            questionText = "What is the Lower Flammability Limit (LFL) of R-454B refrigerant in air?",
            options = listOf("1.8% by volume", "7.7% by volume (approx. 303 g/m³)", "15.0% by volume", "50.0% by volume"),
            correctIndex = 1,
            explanation = "R-454B has an LFL of approximately 7.7% by volume (303 grams per cubic meter), requiring substantial concentration before an air-fuel mixture can ignite.",
            citation = "ASHRAE Standard 34",
            isA2L = true
        ),
        Question(
            id = "t2-63",
            section = EPASection.TYPE_II,
            questionText = "What is the Lower Flammability Limit (LFL) of R-32 refrigerant in air?",
            options = listOf("2.1% by volume", "14.4% by volume (approx. 307 g/m³)", "25.0% by volume", "5.0% by volume"),
            correctIndex = 1,
            explanation = "R-32 has an LFL of approximately 14.4% by volume (307 grams per cubic meter), exhibiting a high resistance to ignition compared to Class 3 hydrocarbons.",
            citation = "ASHRAE Standard 34",
            isA2L = true
        ),
        Question(
            id = "t2-64",
            section = EPASection.TYPE_II,
            questionText = "Under ASHRAE Standard 15-2022, what is the maximum allowable refrigerant charge for an A2L comfort cooling split system without requiring engineered mitigation ventilation?",
            options = listOf("The Maximum Releasable Charge (m1) based on room volume and occupied floor area", "100 pounds in all rooms", "Exactly 5 pounds", "Unlimited charge is permitted in all basements"),
            correctIndex = 0,
            explanation = "Charge limits are determined by ASHRAE 15 formulas: m1, m2, and m3 thresholds calculate maximum charge based on room volume to ensure leaks never reach 25% of the LFL.",
            citation = "ASHRAE Standard 15-2022 Section 7",
            isA2L = true
        ),
        Question(
            id = "t2-65",
            section = EPASection.TYPE_II,
            questionText = "What type of vacuum pump oil is recommended when pulling deep vacuums on commercial refrigeration systems containing POE synthetic lubricants?",
            options = listOf("Used motor oil from a truck", "High-vacuum distilled hydrocarbon pump oil with low vapor pressure (< 5 microns blank-off rating)", "Vegetable oil", "Hydraulic brake fluid"),
            correctIndex = 1,
            explanation = "Deep vacuum pumps require high-purity distilled oil with extremely low vapor pressure to achieve blank-off vacuums below 10-25 microns without boiling.",
            citation = "Robinair Vacuum Technology Handbook",
            isA2L = false
        ),
        Question(
            id = "t2-66",
            section = EPASection.TYPE_II,
            questionText = "Why must technicians NEVER use an open flame torch to braze refrigerant piping on an A2L split system before purging with nitrogen and confirming zero pressure?",
            options = listOf("The pipe will bend", "Residual A2L refrigerant mixed with air in the pipe can ignite or explode, and thermal breakdown produces caustic hydrofluoric acid gas", "The nitrogen will turn into oxygen", "The silver solder will vaporize into steam"),
            correctIndex = 1,
            explanation = "Applying an open flame to lines containing trapped A2L and air introduces an ignition source and generates highly toxic hydrofluoric acid vapors.",
            citation = "AHRI A2L Safe Handling Manual",
            isA2L = true
        ),
        Question(
            id = "t2-67",
            section = EPASection.TYPE_II,
            questionText = "What is the purpose of an isolator ball valve installed with Schrader core fittings on both sides of a commercial receiver?",
            options = listOf("To allow charging liquid while isolating the rest of the system during major repairs", "To allow technicians to vent refrigerant during hot weather", "To bypass the condenser", "To reduce the high-side pressure to zero"),
            correctIndex = 0,
            explanation = "Service valves allow pump-down and isolation of large liquid volumes into the receiver, saving recovery time during evaporator or expansion valve repairs.",
            citation = "RSES Commercial Practices",
            isA2L = false
        ),
        Question(
            id = "t2-68",
            section = EPASection.TYPE_II,
            questionText = "What is a 'pump-down' procedure in commercial refrigeration?",
            options = listOf("Pumping oil from the compressor into the sewer", "Using the compressor to pump the entire operating refrigerant charge into the receiver or condenser and closing the liquid line service valve for repair", "Pumping air into the suction line", "Running the water pump backward"),
            correctIndex = 1,
            explanation = "A pump-down isolates refrigerant inside the high-side receiver and condenser, allowing service on the low-side components without evacuating the entire system charge.",
            citation = "HVAC Commercial Installation Guide",
            isA2L = false
        ),
        Question(
            id = "t2-69",
            section = EPASection.TYPE_II,
            questionText = "Under what condition is a technician prohibited from performing an in-system pump-down to isolate refrigerant?",
            options = listOf("When the ambient temperature is 75°F", "When the receiver is not large enough to hold the entire liquid charge without exceeding 80% volume, or if the condenser/receiver lacks isolation valves", "When the system is charged with R-410A", "Whenever the customer is watching"),
            correctIndex = 1,
            explanation = "If the receiver cannot safely accommodate the entire charge within 80% capacity, liquid will back up into the condenser and trigger high-pressure relief discharge.",
            citation = "ASHRAE Standard 15 § 9.8",
            isA2L = false
        ),
        Question(
            id = "t2-70",
            section = EPASection.TYPE_II,
            questionText = "What is the primary indicator of a severe leak in a water-cooled commercial chiller condenser?",
            options = listOf("The chiller water temperature drops to -40°F", "Refrigerant bubbles appear in the cooling tower water, head pressure drops, and moisture enters the refrigeration circuit through tube pinholes", "The oil level in the compressor rises by 5 gallons in 5 minutes", "The electrical voltage drops to zero"),
            correctIndex = 1,
            explanation = "When water tube pinholes occur, higher water pressure forces water into low-pressure chillers, or high-pressure refrigerant leaks into cooling tower water loops.",
            citation = "Chiller Troubleshooting Manual",
            isA2L = false
        ),
        Question(
            id = "t2-71",
            section = EPASection.TYPE_II,
            questionText = "What is the typical test pressure used when leak testing a high-pressure commercial R-410A system with dry nitrogen?",
            options = listOf("50 psig", "150 to 300 psig (never exceeding the low-side design test pressure on the nameplate)", "1,000 psig", "5 psig"),
            correctIndex = 1,
            explanation = "Testing pressures must not exceed the stamped design test pressure (typically 150-300 psig on low side, 450 psig on high side) to prevent bursting heat exchangers.",
            citation = "ACCA Quality Standards",
            isA2L = false
        ),
        Question(
            id = "t2-72",
            section = EPASection.TYPE_II,
            questionText = "What occurs when nitrogen is used to pressure test an HVAC system without installing an external pressure regulator?",
            options = listOf("The nitrogen will freeze into solid nitrogen ice", "Full nitrogen tank pressure (over 2,000 psig) can violently rupture evaporator tubing, receiver shells, or compressor housings, causing fatal injuries", "The electronic leak detector will short-circuit", "The manifold gauges will run backward"),
            correctIndex = 1,
            explanation = "Unregulated nitrogen carries enough energy to detonate HVAC components like an explosive fragmentation bomb. Regulators and relief valves are mandatory.",
            citation = "OSHA 1910 Compressed Gas Safety Directives",
            isA2L = false
        ),
        Question(
            id = "t2-73",
            section = EPASection.TYPE_II,
            questionText = "Under 40 CFR § 82.157, what verification test must be conducted immediately after completing a leak repair and recharging sufficient refrigerant to pressurize the system?",
            options = listOf("The Follow-up Verification Test", "The Initial Verification Test (conducted before full operational recharging to prove the repair is sound)", "An eddy current test", "A chemical oil titration test"),
            correctIndex = 1,
            explanation = "The Initial Verification test is performed prior to completing the full charge to verify that the repaired joint holds pressure before releasing the appliance.",
            citation = "40 CFR § 82.157(e)(1)",
            isA2L = false
        ),
        Question(
            id = "t2-74",
            section = EPASection.TYPE_II,
            questionText = "Under 40 CFR § 82.157, within what timeframe must the Follow-up Verification Test be conducted on a repaired commercial refrigeration system?",
            options = listOf("Within 1 hour of startup", "Within 10 days of the appliance operating at normal operating characteristics and conditions", "After exactly 365 days", "Within 30 days of the next calendar year"),
            correctIndex = 1,
            explanation = "Federal law mandates the Follow-up Verification test occur within 10 days of the system reaching normal operating temperature and pressure conditions.",
            citation = "40 CFR § 82.157(e)(2)",
            isA2L = false
        ),
        Question(
            id = "t2-75",
            section = EPASection.TYPE_II,
            questionText = "What must happen if either the Initial Verification Test or the Follow-up Verification Test fails on a commercial refrigeration system?",
            options = listOf("The technician must vent all refrigerant and abandon the job", "The 30-day repair clock continues or resets, requiring another repair attempt or development of a retrofit/retirement plan within the statutory window", "The EPA immediately revokes the technician's certification", "The owner is fined \$100,000 within 24 hours"),
            correctIndex = 1,
            explanation = "A failed verification test means the leak is not repaired. The owner must re-repair within the remaining 30 days or develop a certified 1-year retrofit plan.",
            citation = "40 CFR § 82.157(e)",
            isA2L = false
        ),
        Question(
            id = "t2-76",
            section = EPASection.TYPE_II,
            questionText = "What is the primary indicator of an overcharged commercial system with a fixed orifice metering device?",
            options = listOf("Low discharge pressure and low suction pressure", "High head pressure, elevated suction pressure, low superheat, and excessive subcooling", "Suction line frost all the way to the condenser", "High superheat and low subcooling"),
            correctIndex = 1,
            explanation = "Excess liquid backs up in the condenser (high subcooling, high head pressure) and over-floods the evaporator (elevated suction pressure, low superheat).",
            citation = "HVAC Service Procedures Manual",
            isA2L = false
        ),
        Question(
            id = "t2-77",
            section = EPASection.TYPE_II,
            questionText = "What is the primary indicator of an undercharged commercial system with a thermostatic expansion valve (TXV)?",
            options = listOf("High suction pressure and high subcooling", "Low head pressure, normal or low suction pressure, high superheat at the evaporator outlet, and zero to low subcooling in the liquid line", "Frosted condenser coil", "High current draw exceeding compressor RLA"),
            correctIndex = 1,
            explanation = "Insufficient liquid leaves the condenser empty (low subcooling) and starves the TXV, causing high vapor superheat and low suction pressure.",
            citation = "RSES Service Fundamentals",
            isA2L = false
        ),
        Question(
            id = "t2-78",
            section = EPASection.TYPE_II,
            questionText = "What is the purpose of measuring 'subcooling' at the liquid line service valve before the expansion device?",
            options = listOf("To check if the compressor oil is warm", "To verify that 100% pure liquid refrigerant enters the expansion device without flashing into vapor bubbles ahead of the valve orifice", "To measure the efficiency of the indoor blower", "To determine the altitude of the building"),
            correctIndex = 1,
            explanation = "Subcooling measures the degrees of liquid cooling below saturation temperature. Adequate subcooling guarantees a solid liquid column enters the TXV.",
            citation = "Sporlan Valve Application Guide",
            isA2L = false
        ),
        Question(
            id = "t2-79",
            section = EPASection.TYPE_II,
            questionText = "What is the typical design subcooling range for a high-efficiency residential split system with a TXV at full design load?",
            options = listOf("0°F to 2°F", "8°F to 14°F (as specified by manufacturer nameplate)", "30°F to 50°F", "Zero subcooling under all conditions"),
            correctIndex = 1,
            explanation = "Most modern residential split systems mandate 8°F to 14°F of subcooling to ensure optimal thermodynamic efficiency and TXV performance.",
            citation = "ACCA Quality Installation Guidelines",
            isA2L = false
        ),
        Question(
            id = "t2-80",
            section = EPASection.TYPE_II,
            questionText = "What is the purpose of measuring 'superheat' at the suction line service port of a compressor?",
            options = listOf("To measure the temperature of the outdoor air", "To verify that all liquid has boiled off in the evaporator and that only vapor enters the compressor, preventing liquid slugging damage", "To check if the condenser fan is running backward", "To calculate the total weight of the refrigerant charge"),
            correctIndex = 1,
            explanation = "Superheat measures sensible heat added to vapor above boiling saturation. Superheat guarantees no damaging liquid droplets enter the compressor cylinders.",
            citation = "Compressor Reliability Engineering Bulletin",
            isA2L = false
        ),
        Question(
            id = "t2-81",
            section = EPASection.TYPE_II,
            questionText = "What is the typical design total superheat measured at the compressor suction inlet for a commercial refrigeration compressor?",
            options = listOf("0°F to 2°F", "20°F to 30°F (with minimum 20°F to protect compressor oil from dilution)", "75°F to 100°F", "Negative 10°F"),
            correctIndex = 1,
            explanation = "Compressor manufacturers mandate at least 20°F of total superheat at the suction service valve to ensure all liquid droplets are vaporized before entering the crankcase.",
            citation = "Copeland Application Engineering Bulletin 4-1387",
            isA2L = false
        ),
        Question(
            id = "t2-82",
            section = EPASection.TYPE_II,
            questionText = "What does an abnormally high suction superheat (e.g. 45°F) combined with normal or low suction pressure indicate?",
            options = listOf("An overcharged system", "An evaporator that is starved for refrigerant due to a leak, clogged filter-drier, or stuck-closed expansion valve", "The condenser fan motor is disconnected", "The indoor filter is completely plugged with dirt"),
            correctIndex = 1,
            explanation = "A starved evaporator boils off refrigerant too early in the coil, allowing the remaining coil length to heat the dry vapor to high superheat levels.",
            citation = "RSES Troubleshooting Guide",
            isA2L = false
        ),
        Question(
            id = "t2-83",
            section = EPASection.TYPE_II,
            questionText = "What does zero superheat at the compressor suction port indicate?",
            options = listOf("The system is operating at maximum thermodynamic efficiency", "Liquid refrigerant is flooding back into the compressor crankcase, threatening immediate mechanical damage to valves and bearings", "The evaporator coil is completely clean", "The ambient temperature is 100°F"),
            correctIndex = 1,
            explanation = "Zero superheat means saturation temperature equals measured temperature; liquid refrigerant is present in the suction vapor, washing away compressor lubrication.",
            citation = "Compressor Failure Analysis Handbook",
            isA2L = false
        ),
        Question(
            id = "t2-84",
            section = EPASection.TYPE_II,
            questionText = "Why are zeotropic blends like R-407A and R-407C prone to 'glide glide' problems in flooded evaporator chillers?",
            options = listOf("Because the oil dissolves the copper tubes", "Because flooded evaporators maintain a liquid bath where more volatile components boil off preferentially, permanently shifting the liquid pool blend composition", "Because zeotropic blends turn to ice at 40°F", "Because flooded chillers operate in a vacuum"),
            correctIndex = 1,
            explanation = "In flooded coils, volatile components evaporate faster than heavier components, altering the blend ratio in the evaporator pool and destroying design balance.",
            citation = "ASHRAE Equipment Handbook",
            isA2L = false
        ),
        Question(
            id = "t2-85",
            section = EPASection.TYPE_II,
            questionText = "Why is R-410A considered a 'near-azeotropic' blend rather than a traditional high-glide zeotropic blend?",
            options = listOf("Because it has zero global warming potential", "Because its constituent refrigerants (R-32 and R-125) have very similar boiling points, resulting in negligible temperature glide (< 0.5°F)", "Because it is an inorganic element like nitrogen", "Because it never changes state from liquid to vapor"),
            correctIndex = 1,
            explanation = "R-410A has a temperature glide under 0.3°F, behaving almost identically to a true azeotrope during field evaporation and condensing.",
            citation = "ASHRAE Standard 34",
            isA2L = false
        ),
        Question(
            id = "t2-86",
            section = EPASection.TYPE_II,
            questionText = "What safety precaution must be taken when recovering refrigerant from a system whose compressor experienced an internal mechanical failure (locked rotor)?",
            options = listOf("The recovery cylinder must be inverted", "The oil and refrigerant must be tested for acid, and recovery equipment must be protected with an inline sacrificial acid core filter-drier", "The technician must wear rubber scuba diving gear", "The refrigerant must be vented into a metal barrel"),
            correctIndex = 1,
            explanation = "Mechanical friction and local motor heating generate particulate metal shavings and acid that can destroy recovery machine compressors unless an inline filter is used.",
            citation = "AHRI Standard 740 Guidelines",
            isA2L = false
        ),
        Question(
            id = "t2-87",
            section = EPASection.TYPE_II,
            questionText = "What is the function of an inline suction filter installed ahead of a recovery machine?",
            options = listOf("To add fragrance to the recovered gas", "To protect the recovery machine compressor valves from copper filings, brazing scale, acid, and sludge sucked from the damaged appliance", "To cool the recovery hoses", "To increase the pressure of the recovery tank"),
            correctIndex = 1,
            explanation = "Recovery machines have precision piston valves that are easily scored and destroyed by system debris. Disposable inline filter-driers protect the machine.",
            citation = "Appion / Fieldpiece Service Recommendations",
            isA2L = false
        ),
        Question(
            id = "t2-88",
            section = EPASection.TYPE_II,
            questionText = "Under 40 CFR § 82.158, what is the recovery efficiency requirement for high-pressure recovery units manufactured on or after November 15, 1993?",
            options = listOf("Must achieve 10 inches Hg vacuum on systems with 200+ lbs, and 0 psig on systems with < 200 lbs", "Must achieve 29 inches of vacuum in 10 seconds", "Must recover 100% of oil without traces", "Pre-1993 and post-1993 equipment have identical requirements"),
            correctIndex = 0,
            explanation = "Post-1993 equipment must meet AHRI 740 standards, pulling 10 inches of Hg on appliances containing 200 or more pounds of high-pressure refrigerant.",
            citation = "40 CFR § 82.158 Table 2",
            isA2L = false
        ),
        Question(
            id = "t2-89",
            section = EPASection.TYPE_II,
            questionText = "What happens if a technician recovers refrigerant from a high-pressure commercial system with cold liquid lines and does not allow time for oil vaporization?",
            options = listOf("The recovery machine runs faster", "Large quantities of dissolved refrigerant will remain trapped in the compressor crankcase oil, escaping into the room as vapor when the compressor is opened", "The recovery cylinder will explode", "The oil will turn into solid copper"),
            correctIndex = 1,
            explanation = "Cold oil retains substantial dissolved refrigerant. Heating the crankcase or pulling vacuum over extended time is necessary to boil out trapped vapor.",
            citation = "ASHRAE Refrigeration Manual",
            isA2L = false
        ),
        Question(
            id = "t2-90",
            section = EPASection.TYPE_II,
            questionText = "What is the purpose of the push-pull recovery method on large commercial systems containing over 50 pounds of liquid refrigerant?",
            options = listOf("To pull vacuum through two recovery machines connected in series", "To use the recovery machine to compress vapor from the recovery cylinder and inject it into the appliance high side, pushing liquid into the cylinder rapidly through a sight glass", "To push refrigerant into the sewer", "To pull oil out of the evaporator"),
            correctIndex = 1,
            explanation = "Push-pull recovery moves liquid by differential pressure rather than passing liquid through the recovery compressor, speeding transfer rates up to 5-fold.",
            citation = "RSES Recovery Procedures Bulletin",
            isA2L = false
        ),
        Question(
            id = "t2-91",
            section = EPASection.TYPE_II,
            questionText = "Under what condition can the push-pull recovery method NOT be used on a commercial system?",
            options = listOf("When the system has less than 10 to 15 pounds of charge, when the system has no liquid receiver, or when reversing valves or check valves block reverse vapor flow", "When the recovery cylinder is new", "When ambient temperature is 70°F", "When using an electronic scale"),
            correctIndex = 0,
            explanation = "Push-pull requires a continuous open piping loop and a minimum liquid volume; small charges or internal check/reversing valves prevent loop circulation.",
            citation = "Fieldpiece Application Bulletin",
            isA2L = false
        ),
        Question(
            id = "t2-92",
            section = EPASection.TYPE_II,
            questionText = "What is the statutory requirement when disposing of a commercial rooftop unit with a charge of 35 lbs of R-22?",
            options = listOf("The unit may be abandoned on the roof if empty", "All refrigerant must be recovered down to 0 psig by a certified technician, and a recovery verification record maintained before scrap disposal", "The compressor must be drilled open", "The owner must notify the FBI"),
            correctIndex = 1,
            explanation = "EPA rules mandate recovering all refrigerant from high-pressure systems to 0 psig before recycling or disposal under 40 CFR § 82.156(f).",
            citation = "40 CFR § 82.156(f)",
            isA2L = false
        ),
        Question(
            id = "t2-93",
            section = EPASection.TYPE_II,
            questionText = "What is the primary factor determining whether an industrial process refrigeration system qualifies for an extension beyond the 30-day repair window under 40 CFR § 82.157?",
            options = listOf("The cost of the repair exceeds \$500", "The appliance is located in an area subject to radiological contamination, or an OSHA/local safety regulation prevents immediate access, or parts are backordered", "The technician is on summer vacation", "The owner forgot to schedule the service"),
            correctIndex = 1,
            explanation = "Extensions are strictly limited to statutory reasons: radiological contamination, unavailability of replacement components, or mandatory plant shutdown requirements.",
            citation = "40 CFR § 82.157(f)",
            isA2L = false
        ),
        Question(
            id = "t2-94",
            section = EPASection.TYPE_II,
            questionText = "What documentation must a certified technician provide to the owner of an appliance containing 50 or more pounds of refrigerant after adding refrigerant?",
            options = listOf("A handwritten receipt on cardboard", "An invoice or service ticket stating the technician's name, company, date, appliance identifier, full charge, quantity of refrigerant added, and calculated leak rate", "A business card only", "No documentation is legally required if paid in cash"),
            correctIndex = 1,
            explanation = "Under 40 CFR § 82.157(l), technicians must provide written documentation specifying the date, service type, and amount of refrigerant added to enable owner compliance.",
            citation = "40 CFR § 82.157(l)(1)",
            isA2L = false
        ),
        Question(
            id = "t2-95",
            section = EPASection.TYPE_II,
            questionText = "What is the penalty for an equipment owner who fails to maintain required leak inspection records for a 100-lb supermarket refrigeration rack?",
            options = listOf("A formal verbal warning only", "Federal civil penalties of up to \$59,000+ per day per violation under Clean Air Act Section 113 and 40 CFR Part 19", "Loss of electrical service to the building", "Mandatory donation of groceries to the EPA"),
            correctIndex = 1,
            explanation = "Recordkeeping violations under Section 608 carry the same statutory daily civil penalties as illegal venting violations.",
            citation = "Clean Air Act § 113 / 40 CFR Part 19",
            isA2L = false
        ),
        Question(
            id = "t2-96",
            section = EPASection.TYPE_II,
            questionText = "What safety protocol must be followed when replacing an oil pressure safety switch on a commercial compressor?",
            options = listOf("The compressor must be run at maximum speed", "The compressor must be isolated with service valves, pressure relieved safely through a recovery unit to 0 psig, and electrical power locked out / tagged out (LOTO)", "The oil should be drained onto the mechanical room floor", "The capillary line should be cut with wire cutters while pressurized"),
            correctIndex = 1,
            explanation = "OSHA LOTO standards and EPA recovery rules mandate electrical disconnection and zero-pressure verification before opening pressurized fittings.",
            citation = "OSHA 1910.147 Control of Hazardous Energy",
            isA2L = false
        ),
        Question(
            id = "t2-97",
            section = EPASection.TYPE_II,
            questionText = "What is the function of an electronic oil failure control switch (such as a Sentronic switch) on a semi-hermetic commercial compressor?",
            options = listOf("To turn on the oil heater when cold", "To measure net oil pressure (oil pump discharge pressure minus crankcase pressure) and trip off the motor after 90 to 120 seconds if net pressure falls below 8-12 psid", "To add synthetic oil from a storage tank automatically", "To vent refrigerant if oil pressure gets too high"),
            correctIndex = 1,
            explanation = "Net oil pressure ensures bearing lubrication. If net differential pressure drops below safe thresholds, the timer trips to prevent bearing destruction.",
            citation = "Copeland Application Engineering Bulletin 4-1311",
            isA2L = false
        ),
        Question(
            id = "t2-98",
            section = EPASection.TYPE_II,
            questionText = "Why does a commercial compressor draw significantly higher electrical current (amperage) when operating with dirty condenser coils during a heatwave?",
            options = listOf("Because electricity flows faster in the heat", "Elevated condensing pressure increases the compression ratio and mass flow resistance, requiring greater mechanical horsepower and motor electrical torque", "Because dirty coils increase the voltage from the utility", "Because the fan motor runs in reverse"),
            correctIndex = 1,
            explanation = "High head pressure forces pistons to pump against higher resistance, elevating electrical current draw and potentially tripping internal motor overloads.",
            citation = "RSES Commercial Electrical Troubleshooting",
            isA2L = false
        ),
        Question(
            id = "t2-99",
            section = EPASection.TYPE_II,
            questionText = "What is the ultimate purpose of the EPA Section 608 Type II certification?",
            options = listOf("To allow technicians to purchase automotive cans of R-134a", "To certify that technicians have demonstrated competence in the safe, legal, and environmentally responsible handling of high-pressure refrigerants in commercial and residential equipment", "To authorize technicians to design nuclear reactors", "To grant a license to install natural gas plumbing"),
            correctIndex = 1,
            explanation = "Type II certification authorizes technicians to maintain, service, repair, and dispose of high-pressure appliances under federal Clean Air Act regulations.",
            citation = "40 CFR § 82.161",
            isA2L = false
        ),
        Question(
            id = "t2-100",
            section = EPASection.TYPE_II,
            questionText = "What is the single most critical practice to ensure an EPA Section 608 Type II system will operate reliably for 15+ years after installation or major repair?",
            options = listOf("Add 5 lbs of extra refrigerant just in case", "Perform a dry nitrogen pressure hold at design test pressure, pull a deep vacuum below 500 microns with a calibrated micron gauge, and weigh in the factory-specified charge", "Purge air through the high-side service valve with refrigerant", "Install three sight glasses in series"),
            correctIndex = 1,
            explanation = "Standing pressure verification, deep molecular dehydration to 500 microns, and precise charge measurement ensure the system remains leak-free, acid-free, and thermally efficient.",
            citation = "ACCA Quality Installation Standards",
            isA2L = false
        )
    )
}
