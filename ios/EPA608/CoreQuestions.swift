import Foundation

public struct CoreQuestions {
    public static let list: [Question] = [
        Question(
            id: "core-1",
            section: .core,
            questionText: "Which chemical component in older refrigerants is responsible for the catalytic destruction of stratospheric ozone?",
            options: ["Fluorine", "Chlorine", "Hydrogen", "Carbon Dioxide"],
            correctIndex: 1,
            explanation: "Chlorine is the catalyst that destroys stratospheric ozone. Fluorine does not harm the ozone layer, which is why HFCs have an ODP of zero.",
            citation: "40 CFR Part 82 Title VI",
            isA2L: false
        ),
        Question(
            id: "core-2",
            section: .core,
            questionText: "What is the Global Warming Potential (GWP) reference standard gas assigned a baseline value of 1.0?",
            options: ["Methane (CH4)", "Carbon Dioxide (CO2)", "CFC-12", "Nitrous Oxide (N2O)"],
            correctIndex: 1,
            explanation: "Carbon dioxide (CO2) is the international baseline reference gas with a Global Warming Potential (GWP) defined exactly as 1.0.",
            citation: "EPA AIM Act Guidance",
            isA2L: false
        ),
        Question(
            id: "core-3",
            section: .core,
            questionText: "When leak checking an HVAC system with pressurized dry nitrogen, what safety device must always be installed between the nitrogen cylinder and the system?",
            options: ["A backflow preventer", "A pressure regulator and relief valve", "A sight glass", "A Schrader core depressor"],
            correctIndex: 1,
            explanation: "Nitrogen cylinders hold over 2,000 psig. Connecting directly without a pressure regulator and downstream relief valve can cause catastrophic explosion of system piping or heat exchangers.",
            citation: "EPA Section 608 Safety Guidelines",
            isA2L: false
        ),
        Question(
            id: "core-4",
            section: .core,
            questionText: "What standard recovery cylinder paint scheme is required by the Air-Conditioning, Heating, and Refrigeration Institute (AHRI)?",
            options: ["Solid blue body with a green shoulder", "Gray lower body with a yellow top/shoulder", "Solid yellow body with a gray shoulder", "White body with an orange collar"],
            correctIndex: 1,
            explanation: "AHRI Guideline N and DOT specify recovery cylinders must have a gray lower body and yellow upper shoulder/top.",
            citation: "AHRI Guideline N / DOT Regulations",
            isA2L: false
        ),
        Question(
            id: "core-5",
            section: .core,
            questionText: "Under the EPA AIM Act Technology Transitions rule (40 CFR Part 84), what is the maximum Global Warming Potential (GWP) limit for refrigerants used in new residential air conditioning and heat pumps?",
            options: ["150 GWP", "700 GWP", "1,500 GWP", "2,500 GWP"],
            correctIndex: 1,
            explanation: "Under 40 CFR Part 84 Subpart B, new residential and light commercial AC systems have a GWP limit of 700, effectively phasing out R-410A (GWP 2,088) in favor of A2L alternatives like R-454B (GWP 466) and R-32 (GWP 675).",
            citation: "40 CFR § 84.54",
            isA2L: true
        ),
        Question(
            id: "core-6",
            section: .core,
            questionText: "What is the maximum penalty per day, per violation, that the EPA can assess under Clean Air Act Section 113 for knowingly venting regulated refrigerants?",
            options: ["$10,000", "$27,500", "$37,500", "Over $50,000 (inflation-adjusted under 40 CFR Part 19)"],
            correctIndex: 3,
            explanation: "Under Clean Air Act Section 113 and federal civil penalty inflation adjustments (40 CFR Part 19), daily administrative fines can exceed $59,000 per violation per day.",
            citation: "Clean Air Act § 113 / 40 CFR Part 19",
            isA2L: false
        ),
        Question(
            id: "core-7",
            section: .core,
            questionText: "Under ASHRAE Standard 15, what action is triggered when an A2L Refrigerant Detection System (RDS) detects a leak reaching 25% of the Lower Flammability Limit (LFL)?",
            options: ["The system vents all remaining refrigerant to the roof", "Continuous mechanical ventilation activates to disperse vapor and the compressor de-energizes", "Electric heat strips immediately energize to burn off vapor", "The expansion valve closes while the outdoor condenser continues running"],
            correctIndex: 1,
            explanation: "ASHRAE Standard 15 and UL 60335-2-40 mandate that upon detecting 25% LFL, the RDS automatically activates continuous indoor mechanical airflow to disperse refrigerant below flammable concentrations and shuts down the compressor.",
            citation: "ASHRAE Standard 15-2022",
            isA2L: true
        ),
        Question(
            id: "core-8",
            section: .core,
            questionText: "Which of the following is an EPA-exempt refrigerant that may be legally vented to the atmosphere during servicing?",
            options: ["R-410A", "R-134a", "Carbon Dioxide (R-744) or Nitrogen", "R-22"],
            correctIndex: 2,
            explanation: "Under 40 CFR § 82.154, natural refrigerants such as carbon dioxide (R-744), nitrogen (R-728), and water are exempt from the venting prohibition because they do not pose an environmental hazard.",
            citation: "40 CFR § 82.154(a)",
            isA2L: false
        ),
        Question(
            id: "core-9",
            section: .core,
            questionText: "How many ozone molecules can be destroyed by a single chlorine atom released from a CFC molecule into the stratosphere?",
            options: ["Up to 10", "Up to 1,000", "Up to 100,000", "Up to 10,000,000"],
            correctIndex: 2,
            explanation: "A single chlorine atom acts as a catalytic agent and can repeatedly break apart up to 100,000 ozone (O3) molecules before being bound into a stable compound.",
            citation: "Clean Air Act Title VI Science",
            isA2L: false
        ),
        Question(
            id: "core-10",
            section: .core,
            questionText: "Why do CFC and HCFC molecules reach the stratosphere without being washed out by rainfall?",
            options: ["They are lighter than air and float rapidly upward", "They do not dissolve in water and are chemically extremely stable in the lower atmosphere", "They bond with tropospheric dust particles", "They are actively repelled by rain clouds"],
            correctIndex: 1,
            explanation: "CFCs and HCFCs are insoluble in water and do not react with common tropospheric compounds. Their extreme chemical stability allows winds to carry them into the stratosphere intact over decades.",
            citation: "EPA Ozone Science / 40 CFR Part 82",
            isA2L: false
        ),
        Question(
            id: "core-11",
            section: .core,
            questionText: "What international treaty signed in 1987 established the global phaseout framework for ozone-depleting substances?",
            options: ["The Kyoto Protocol", "The Montreal Protocol", "The Paris Agreement", "The Geneva Convention"],
            correctIndex: 1,
            explanation: "The Montreal Protocol on Substances that Deplete the Ozone Layer was adopted in 1987 to phase out production and consumption of CFCs, halons, and HCFCs.",
            citation: "UNEP Montreal Protocol",
            isA2L: false
        ),
        Question(
            id: "core-12",
            section: .core,
            questionText: "Which title of the United States Clean Air Act contains the statutory authority for Section 608 technician certification and venting prohibitions?",
            options: ["Title I", "Title IV", "Title VI", "Title VIII"],
            correctIndex: 2,
            explanation: "Title VI of the Clean Air Act is titled 'Stratospheric Ozone Protection' and contains Section 608 (National Recycling and Emission Reduction Program).",
            citation: "Clean Air Act Title VI § 608",
            isA2L: false
        ),
        Question(
            id: "core-13",
            section: .core,
            questionText: "Under Clean Air Act Section 608, what constitutes an illegal 'de-minimis' release of refrigerant?",
            options: ["Minor releases occurring during the normal connection or disconnection of low-loss hoses", "Intentionally opening a service valve to purge 2 lbs of refrigerant because recovery takes too long", "Vapor loss during normal operation of an oil separator", "Trace vapor released when calibrating an electronic leak detector"],
            correctIndex: 1,
            explanation: "De-minimis release applies ONLY to unavoidable, trace releases occurring during good-faith recovery or hose connection. Deliberately venting any quantity to save time is a criminal violation.",
            citation: "40 CFR § 82.154(a)(2)",
            isA2L: false
        ),
        Question(
            id: "core-14",
            section: .core,
            questionText: "What is the primary objective of the EPA AIM (American Innovation and Manufacturing) Act of 2020?",
            options: ["To phase out all copper piping in residential construction", "To phase down the production and consumption of hydrofluorocarbons (HFCs) by 85% by 2036", "To mandate electric heat pumps in all new commercial buildings", "To eliminate the use of nitrogen in pressure testing"],
            correctIndex: 1,
            explanation: "The AIM Act directs the EPA to phase down hydrofluorocarbon (HFC) supply and demand by 85% over a 15-year period (by 2036) to reduce greenhouse gas emissions.",
            citation: "40 CFR Part 84 Subpart A",
            isA2L: false
        ),
        Question(
            id: "core-15",
            section: .core,
            questionText: "Which baseline refrigerant has an Ozone Depletion Potential (ODP) defined exactly as 1.0?",
            options: ["CFC-11 (Trichlorofluoromethane)", "HCFC-22 (Chlorodifluoromethane)", "HFC-134a (Tetrafluoroethane)", "R-718 (Water)"],
            correctIndex: 0,
            explanation: "CFC-11 is the benchmark compound against which the Ozone Depletion Potential (ODP) of all other chemical substances is calculated, with an assigned ODP of 1.0.",
            citation: "40 CFR Part 82 Subpart A",
            isA2L: false
        ),
        Question(
            id: "core-16",
            section: .core,
            questionText: "Why are Hydrofluoroolefins (HFOs) considered environmentally preferable replacements for HFCs?",
            options: ["They have both zero Ozone Depletion Potential (ODP) and ultra-low Global Warming Potential (GWP < 1)", "They never boil and remain liquid at all atmospheric temperatures", "They are completely non-flammable under all possible conditions", "They generate oxygen when exposed to stratospheric sunlight"],
            correctIndex: 0,
            explanation: "HFOs contain carbon-carbon double bonds that break down within days in the lower atmosphere, giving them zero ODP and GWP values near or below 1.",
            citation: "EPA SNAP Program / 40 CFR Part 84",
            isA2L: true
        ),
        Question(
            id: "core-17",
            section: .core,
            questionText: "What type of certification is legally required to purchase regulated refrigerants in containers larger than 2 pounds?",
            options: ["Any state driver's license with proof of HVAC business registration", "EPA Section 608 Technician Certification (Type I, II, III, or Universal)", "OSHA 30-Hour Construction Safety Card", "A local plumbing journeyman license"],
            correctIndex: 1,
            explanation: "Under 40 CFR § 82.166, only technicians certified under Clean Air Act Section 608 (or Section 609 for MVAC) can legally purchase regulated bulk refrigerants.",
            citation: "40 CFR § 82.166(a)",
            isA2L: false
        ),
        Question(
            id: "core-18",
            section: .core,
            questionText: "What recordkeeping requirement applies to HVAC equipment owners for comfort cooling systems containing 50 or more pounds of refrigerant?",
            options: ["All service, leak inspection, and refrigerant addition records must be retained for at least 3 years", "Records must be mailed to the EPA regional office within 24 hours of servicing", "Records are only required if the equipment was manufactured prior to 1990", "Records must be kept permanently on a brass placard mounted to the compressor"],
            correctIndex: 0,
            explanation: "Under 40 CFR § 82.157(l), owners and operators of appliances containing 50 or more pounds of refrigerant must keep servicing and leak rate records for at least 3 years.",
            citation: "40 CFR § 82.157(l)",
            isA2L: false
        ),
        Question(
            id: "core-19",
            section: .core,
            questionText: "Which agency is responsible for testing and listing recovery and recycling equipment performance under Clean Air Act Section 608?",
            options: ["The Department of Energy (DOE)", "EPA-approved third-party testing organizations (such as AHRI or UL)", "The Federal Communications Commission (FCC)", "The Occupational Safety and Health Administration (OSHA)"],
            correctIndex: 1,
            explanation: "Recovery equipment must be tested and certified by EPA-approved independent third-party laboratories (such as Underwriters Laboratories or AHRI) per standard AHRI 740.",
            citation: "40 CFR § 82.158",
            isA2L: false
        ),
        Question(
            id: "core-20",
            section: .core,
            questionText: "What happens if a technician knowingly mixes different refrigerants (e.g. R-22 and R-410A) into the same recovery cylinder?",
            options: ["The mixture forms an azeotropic blend that works in all compressors", "The mixture cannot be reclaimed and must be destroyed at the technician's substantial expense as hazardous chemical waste", "The recovery cylinder automatically vents excess pressure safely", "The mixture increases the thermal efficiency of the recovery machine"],
            correctIndex: 1,
            explanation: "Mixed refrigerants cannot be separated by standard reclamation facilities. Reclaimers reject contaminated cylinders, requiring costly high-temperature incineration at the contractor's expense.",
            citation: "AHRI Guideline K / EPA Section 608",
            isA2L: false
        ),
        Question(
            id: "core-21",
            section: .core,
            questionText: "Under Department of Transportation (DOT) regulations, what is the maximum percentage by weight to which a refrigerant recovery cylinder may be filled with liquid at 70°F?",
            options: ["70%", "80%", "90%", "95%"],
            correctIndex: 1,
            explanation: "DOT regulations mandate that refillable recovery cylinders never be filled beyond 80% of their liquid capacity by weight at 70°F to allow room for thermal expansion.",
            citation: "49 CFR § 173.304",
            isA2L: false
        ),
        Question(
            id: "core-22",
            section: .core,
            questionText: "How often must refillable refrigerant cylinders undergo hydrostatic pressure re-testing and visual inspection under DOT rules?",
            options: ["Every 12 months", "Every 3 years", "Every 5 years", "Every 10 years"],
            correctIndex: 2,
            explanation: "Refillable DOT-4BA and DOT-4BW recovery cylinders must be hydrostatically tested and recertified every 5 years.",
            citation: "49 CFR § 180.209",
            isA2L: false
        ),
        Question(
            id: "core-23",
            section: .core,
            questionText: "What severe hazard can occur if a disposable (non-refillable) refrigerant cylinder (DOT-39) is connected to a recovery machine and refilled?",
            options: ["The internal relief valve will clog with oil", "The cylinder can rupture violently and explode, causing fatal injuries", "The cylinder will freeze and implode due to vacuum", "The refrigerant inside will instantly turn into acid"],
            correctIndex: 1,
            explanation: "Federal law strictly prohibits refilling disposable DOT-39 cylinders. Their thin walls and non-resealable check valves cannot withstand repeated cycles, creating severe risk of catastrophic explosion.",
            citation: "49 CFR § 178.65 / DOT Regulations",
            isA2L: false
        ),
        Question(
            id: "core-24",
            section: .core,
            questionText: "What dangerous gas is formed when common halogenated refrigerants (CFCs, HCFCs, HFCs) come into contact with open flames or red-hot metal surfaces?",
            options: ["Carbon monoxide and phosgene gas", "Pure nitrogen dioxide", "Sulfur hexafluoride", "Hydrogen peroxide vapor"],
            correctIndex: 0,
            explanation: "Thermal decomposition of halogenated refrigerants at high temperatures produces toxic and corrosive gases including phosgene, hydrogen chloride, and hydrofluoric acid.",
            citation: "EPA Section 608 Safety Guidelines / NIOSH",
            isA2L: false
        ),
        Question(
            id: "core-25",
            section: .core,
            questionText: "Under ASHRAE Standard 34, what does a refrigerant designation of 'B2L' signify?",
            options: ["Higher toxicity and higher flammability", "Lower toxicity and non-flammable", "Higher toxicity and lower (mild) flammability", "Lower toxicity and high flammability"],
            correctIndex: 2,
            explanation: "In ASHRAE 34, 'A' indicates lower toxicity, 'B' indicates higher toxicity. Flammability: '1' = no flame propagation, '2L' = lower flammability with burning velocity <= 10 cm/s, '2' = lower flammability, '3' = high flammability.",
            citation: "ASHRAE Standard 34",
            isA2L: true
        ),
        Question(
            id: "core-26",
            section: .core,
            questionText: "What distinguishes the valve connection threads on recovery cylinders designated for A2L mildly flammable refrigerants per CGA-164?",
            options: ["Standard right-hand 1/4\" flare threads", "Left-handed reverse threads to prevent cross-connection with standard A1 equipment", "Bayonet-style quick-disconnect fittings only", "Flanged bolt-on fittings with neoprene seals"],
            correctIndex: 1,
            explanation: "CGA-164 specifies left-handed (reverse) valve connection threads for A2L cylinders so technicians cannot mistakenly attach hoses intended for non-flammable A1 refrigerants.",
            citation: "Compressed Gas Association (CGA) Standard 164",
            isA2L: true
        ),
        Question(
            id: "core-27",
            section: .core,
            questionText: "When transporting refrigerant cylinders in a service vehicle, how must they be positioned and secured?",
            options: ["Laid horizontally on their sides to distribute liquid weight", "Upright, firmly secured with straps or racks, with valve protection caps installed", "Placed inside an airtight wooden box in the passenger seat", "Inverted upside down to keep the valve submerged in oil"],
            correctIndex: 1,
            explanation: "DOT regulations require compressed gas cylinders to be transported upright and secured against movement, ensuring safety relief valves stay in the vapor space.",
            citation: "49 CFR § 177.834",
            isA2L: false
        ),
        Question(
            id: "core-28",
            section: .core,
            questionText: "Why must oxygen or compressed air NEVER be used to pressurize an HVAC system for leak testing?",
            options: ["They will freeze the expansion valve", "They can react explosively when mixed with compressor lubricating oil under pressure", "They will instantly dissolve copper tubing", "They ruin the vacuum pump gauge"],
            correctIndex: 1,
            explanation: "Compressing oxygen or shop air with lubricating oil forms a combustible diesel effect mixture that can detonate violently inside system piping.",
            citation: "EPA 608 Safety Practices / ACCA Manual",
            isA2L: false
        ),
        Question(
            id: "core-29",
            section: .core,
            questionText: "In the event of a catastrophic refrigerant leak in an enclosed machinery room, what is the primary immediate health risk to personnel?",
            options: ["Sudden loss of hearing", "Asphyxiation caused by refrigerant vapors displacing breathable oxygen", "Permanent discoloration of the skin", "Thermal heat stroke"],
            correctIndex: 1,
            explanation: "Refrigerant vapors are heavier than air and sink to low levels, rapidly displacing oxygen and causing suffocation without noticeable warning odor.",
            citation: "ASHRAE Standard 15 / OSHA 1910.134",
            isA2L: false
        ),
        Question(
            id: "core-30",
            section: .core,
            questionText: "What personal protective equipment (PPE) must always be worn when connecting or disconnecting refrigerant charging hoses?",
            options: ["Safety glasses with side shields and thermal/butyl-lined gloves", "Rubber boots and an aluminized proximity suit", "Cotton gardening gloves and sunglasses", "Earplugs and a dust mask only"],
            correctIndex: 0,
            explanation: "Liquid refrigerant expanding at atmospheric pressure boils at temperatures far below 0°F, causing instantaneous frostbite to skin and irreversible blindness if splashed into eyes.",
            citation: "OSHA 1910.133 & 1910.138",
            isA2L: false
        ),
        Question(
            id: "core-31",
            section: .core,
            questionText: "What is the physical state of refrigerant as it enters the suction side of a recovery machine compressor?",
            options: ["Subcooled liquid", "Superheated vapor", "Saturated solid", "Azeotropic sludge"],
            correctIndex: 1,
            explanation: "Recovery machines are vapor compressors. Introducing liquid directly into the suction port can cause catastrophic hydraulic valve slugging unless equipped with a vaporizing restrictor.",
            citation: "AHRI Standard 740",
            isA2L: false
        ),
        Question(
            id: "core-32",
            section: .core,
            questionText: "Under 40 CFR Part 82, what is an 'azeotropic' refrigerant mixture?",
            options: ["A blend of refrigerants that acts like a single chemical substance with a constant boiling point and no temperature glide", "A blend of oil and refrigerant that never separates", "A refrigerant that decomposes into nitrogen when heated", "A mixture of three refrigerants that fractionate severely across a 20°F glide range"],
            correctIndex: 0,
            explanation: "Azeotropes (e.g. R-500, R-502, R-507) behave as pure compounds during phase change without temperature glide or fractionation.",
            citation: "ASHRAE Standard 34",
            isA2L: false
        ),
        Question(
            id: "core-33",
            section: .core,
            questionText: "Under 40 CFR Part 82, what is a 'zeotropic' refrigerant mixture?",
            options: ["A refrigerant that acts as a pure chemical with no boiling variation", "A blend whose components boil and condense at different temperatures across a range known as temperature glide", "A refrigerant that contains only natural hydrocarbons", "A refrigerant designed exclusively for ultra-low cryogenic applications"],
            correctIndex: 1,
            explanation: "Zeotropic blends (R-400 series like R-407C, R-410A, R-454B) exhibit temperature glide because individual constituent molecules boil at different temperatures.",
            citation: "ASHRAE Standard 34",
            isA2L: false
        ),
        Question(
            id: "core-34",
            section: .core,
            questionText: "Why must zeotropic refrigerant blends always be removed from a storage cylinder as a liquid?",
            options: ["Because vapor extraction would cause fractionation, altering the chemical proportion of the remaining blend", "Because liquid refrigerant is less dense than vapor", "Because vapor will dissolve the valve seat gasket", "Because DOT prohibits moving vapor across state lines"],
            correctIndex: 0,
            explanation: "Charging or withdrawing zeotropic blends as a vapor draws off the most volatile component first, shifting the chemical blend ratio and degrading thermal performance.",
            citation: "EPA 608 Core Principles",
            isA2L: false
        ),
        Question(
            id: "core-35",
            section: .core,
            questionText: "What is meant by the term 'temperature glide' in refrigerant thermodynamics?",
            options: ["The difference between ambient room temperature and outdoor condenser temperature", "The temperature range over which a zeotropic blend evaporates or condenses at a constant pressure", "The speed at which refrigerant travels through the liquid line", "The drop in temperature when refrigerant passes through an expansion valve"],
            correctIndex: 1,
            explanation: "Temperature glide is the difference between the starting bubble point and the finishing dew point temperatures during phase change at constant pressure.",
            citation: "ASHRAE Standard 34",
            isA2L: false
        ),
        Question(
            id: "core-36",
            section: .core,
            questionText: "Under 40 CFR § 82.156, what is required before scrapping an appliance with a full charge?",
            options: ["Refrigerant must be recovered to statutory vacuum levels or 90% charge before final disposal", "The appliance must be buried directly in an EPA-approved landfill", "The refrigerant may be vented if the appliance is over 20 years old", "The compressor must be filled with motor oil to neutralize refrigerant"],
            correctIndex: 0,
            explanation: "Federal law mandates that refrigerant be recovered from all equipment prior to dismantling, recycling, or disposal.",
            citation: "40 CFR § 82.156(f)",
            isA2L: false
        ),
        Question(
            id: "core-37",
            section: .core,
            questionText: "Which instrument is used to measure vacuum levels during deep dehydration below 1 millimeter of mercury (mm Hg)?",
            options: ["A Bourdon tube manifold gauge", "A digital electronic micron gauge", "A liquid manometer filled with oil", "A dial thermometer"],
            correctIndex: 1,
            explanation: "Mechanical Bourdon gauges lack resolution below 1 inch of Hg. Digital micron gauges accurately read absolute vacuum down to 1 micron (1/1000th of a mm Hg).",
            citation: "ACCA Quality Installation Guidelines",
            isA2L: false
        ),
        Question(
            id: "core-38",
            section: .core,
            questionText: "What standard target vacuum level indicates adequate moisture dehydration in modern HVAC systems prior to charging?",
            options: ["500 microns or lower", "5,000 microns", "25 inches of Hg vacuum", "0 psig"],
            correctIndex: 0,
            explanation: "Industry standards mandate evacuating systems to 500 microns or lower and passing a 10-minute vacuum rise test to guarantee moisture dehydration.",
            citation: "ASHRAE HVAC Systems Handbook",
            isA2L: false
        ),
        Question(
            id: "core-39",
            section: .core,
            questionText: "During a vacuum decay test, if the pressure rises from 500 microns to 1,500 microns and then stabilizes, what does this indicate?",
            options: ["A severe piping rupture open to the atmosphere", "Moisture or remaining dissolved refrigerant boiling out of the system oil", "The vacuum pump is malfunctioning", "The system is perfectly dry and ready for charging"],
            correctIndex: 1,
            explanation: "A rise that levels off indicates liquid water or trapped refrigerant boiling off in the vacuum. If it continues rising to atmospheric pressure (760,000 microns), an active leak is present.",
            citation: "EPA 608 Evacuation Guidelines",
            isA2L: false
        ),
        Question(
            id: "core-40",
            section: .core,
            questionText: "What chemical reaction occurs when moisture remains inside an operating refrigeration system containing POE lubricant and HFC refrigerant?",
            options: ["The moisture turns into pure nitrogen gas", "Hydrolysis occurs, creating organic acids that erode compressor motor insulation and cause burnout", "The refrigerant turns into solid ice and expands the tubing permanently", "The compressor runs 50% cooler"],
            correctIndex: 1,
            explanation: "Synthetic polyolester (POE) oils are highly hygroscopic and chemically react with water molecules (hydrolysis) to form carboxylic acids, leading to compressor electrical failure.",
            citation: "AHRI Technical Bulletin",
            isA2L: false
        ),
        Question(
            id: "core-41",
            section: .core,
            questionText: "What is the Global Warming Potential (GWP) of R-410A compared to carbon dioxide?",
            options: ["1.0", "675", "2,088", "14,300"],
            correctIndex: 2,
            explanation: "R-410A has a 100-year GWP of 2,088, which is over 2,000 times more potent than carbon dioxide as a greenhouse gas.",
            citation: "EPA AIM Act Baseline Table",
            isA2L: false
        ),
        Question(
            id: "core-42",
            section: .core,
            questionText: "What is the Global Warming Potential (GWP) of the A2L replacement refrigerant R-454B?",
            options: ["466", "2,088", "1,430", "Zero"],
            correctIndex: 0,
            explanation: "R-454B (an HFC/HFO blend of R-32 and R-1234yf) has a GWP of 466, representing a 78% reduction compared to R-410A.",
            citation: "40 CFR Part 84 Subpart B",
            isA2L: true
        ),
        Question(
            id: "core-43",
            section: .core,
            questionText: "What is the Global Warming Potential (GWP) of pure R-32 refrigerant?",
            options: ["3", "675", "1,810", "3,920"],
            correctIndex: 1,
            explanation: "Pure difluoromethane (R-32) has a GWP of 675, which meets the EPA AIM Act 700 GWP ceiling for new comfort cooling equipment.",
            citation: "40 CFR § 84.54",
            isA2L: true
        ),
        Question(
            id: "core-44",
            section: .core,
            questionText: "Under ASHRAE Standard 15, what is the maximum burning velocity limit that categorizes a refrigerant as Class 2L rather than Class 2?",
            options: ["10 cm per second (approx. 4 inches/sec)", "50 cm per second", "100 cm per second", "1,000 cm per second"],
            correctIndex: 0,
            explanation: "Class 2L refrigerants have lower flammability with a maximum laminar flame burning velocity of 10 cm/s or less, making them difficult to ignite and sustain without a continuous external heat source.",
            citation: "ASHRAE Standard 34-2022",
            isA2L: true
        ),
        Question(
            id: "core-45",
            section: .core,
            questionText: "What color markings identify an ASHRAE Class A2L mildly flammable refrigerant recovery cylinder under AHRI Guideline N and CGA-164?",
            options: ["A bright purple collar", "A red band or shoulder around the yellow top, accompanied by left-handed reverse threads", "A solid green tank with black lettering", "No unique marking is required"],
            correctIndex: 1,
            explanation: "A2L recovery cylinders must feature a red band or shoulder to immediately alert technicians of mildly flammable contents, and CGA-164 reverse threads prevent cross-coupling.",
            citation: "AHRI Guideline N / CGA-164",
            isA2L: true
        ),
        Question(
            id: "core-46",
            section: .core,
            questionText: "What safety feature is required on manifold gauges and recovery units specifically certified for use with A2L refrigerants?",
            options: ["Intrinsically safe sealed electrical switches and brushless DC motors to eliminate arcing/spark sources", "Glass viewing sight glasses on all four hose ports", "An integrated fire extinguisher nozzle", "A manual pressure blowoff valve that vents to the floor"],
            correctIndex: 0,
            explanation: "A2L certified tools must be spark-proof (intrinsically safe) per UL standards, using sealed relays and sealed motors to eliminate electrical sparks that could ignite vapor.",
            citation: "UL 60335-2-40 Annex LL",
            isA2L: true
        ),
        Question(
            id: "core-47",
            section: .core,
            questionText: "Which of the following is considered a non-exempt, fully regulated refrigerant under Clean Air Act Section 608?",
            options: ["Propane (R-290) in industrial process chillers", "HFO-1234yf", "Carbon dioxide (R-744)", "Nitrogen (R-728)"],
            correctIndex: 1,
            explanation: "EPA regulations prohibit the intentional venting of fluorinated greenhouse gases, including hydrofluoroolefins (HFOs) like HFO-1234yf and HFO-1233zd, during servicing.",
            citation: "40 CFR § 82.154(a)",
            isA2L: false
        ),
        Question(
            id: "core-48",
            section: .core,
            questionText: "Under 40 CFR § 82.158, what is the required maximum leak rate for recovery equipment manufactured on or after November 15, 1993?",
            options: ["Equipment must not lose more than 3% of its weight per month", "Equipment must be equipped with low-loss shutoff fittings that close automatically or manually within 12 inches of the hose end", "Equipment must have dual vacuum pumps operating in tandem", "Hoses may be open-ended if shorter than 3 feet"],
            correctIndex: 1,
            explanation: "Under EPA standards, recovery hoses must have low-loss fittings that prevent refrigerant loss when connecting or disconnecting from appliances.",
            citation: "40 CFR § 82.158",
            isA2L: false
        ),
        Question(
            id: "core-49",
            section: .core,
            questionText: "What is the statutory definition of 'reclaim' under Clean Air Act Section 608 regulations?",
            options: ["To clean refrigerant by oil separation and single or multiple passes through filter-driers on site", "To reprocess refrigerant to all of the original specifications of AHRI Standard 700 purity verified by chemical laboratory analysis", "To pump refrigerant into a storage cylinder for reuse in the same customer's equipment", "To burn off refrigerant vapors using an oxy-acetylene torch"],
            correctIndex: 1,
            explanation: "'Reclaim' legally means reprocessing recovered refrigerant to AHRI 700 purity standards with mandatory laboratory certification. Only reclaimed refrigerant may be sold to new owners.",
            citation: "40 CFR § 82.152",
            isA2L: false
        ),
        Question(
            id: "core-50",
            section: .core,
            questionText: "What is the statutory definition of 'recycle' under Clean Air Act Section 608?",
            options: ["To clean refrigerant for reuse by reducing moisture, acidity, and particulate matter through oil separation and filter-drier passes, typically in the field", "To return refrigerant to virgin manufacturing specifications with chemical laboratory testing", "To melt old recovery tanks into scrap copper", "To transfer refrigerant from a disposable tank to a recovery tank"],
            correctIndex: 0,
            explanation: "'Recycle' refers to field cleaning through filtration and oil extraction for reintroduction into the same appliance or appliances owned by the same entity.",
            citation: "40 CFR § 82.152",
            isA2L: false
        ),
        Question(
            id: "core-51",
            section: .core,
            questionText: "Under what condition can recovered refrigerant be legally resold to a different equipment owner?",
            options: ["As long as the technician visually checks that the oil is clear", "Only after it has been reclaimed by an EPA-certified reclaimer and analyzed to meet AHRI Standard 700 purity specifications", "If the technician discounts the price by 50%", "Whenever the technician receives written consent from the first owner"],
            correctIndex: 1,
            explanation: "Federal law strictly prohibits selling recovered refrigerant to another owner unless it has been processed to virgin chemical purity (AHRI 700) by an EPA-certified reclaimer.",
            citation: "40 CFR § 82.154(g)",
            isA2L: false
        ),
        Question(
            id: "core-52",
            section: .core,
            questionText: "What is the primary danger of using a heat gun or torch directly on a refrigerant recovery cylinder to speed up charging?",
            options: ["The paint on the cylinder will burn and release fumes", "Localized overheating can trigger catastrophic cylinder rupture or cause safety relief valves to discharge toxic vapor", "The heat turns the refrigerant into carbon monoxide", "The oil inside the cylinder will crystallize"],
            correctIndex: 1,
            explanation: "Open flame or excessive direct heat on cylinders creates localized high pressures that can overwhelm pressure relief devices and trigger catastrophic mechanical vessel explosion.",
            citation: "EPA Section 608 Safety Practices",
            isA2L: false
        ),
        Question(
            id: "core-53",
            section: .core,
            questionText: "What is the safe maximum temperature to which a refrigerant cylinder should be heated when using an approved warm water bath?",
            options: ["100°F", "125°F", "180°F", "212°F (boiling)"],
            correctIndex: 1,
            explanation: "DOT and CGA guidelines state that refrigerant cylinders should never be subjected to temperatures exceeding 125°F (52°C) to prevent overpressurization.",
            citation: "CGA Pamphlet P-1",
            isA2L: false
        ),
        Question(
            id: "core-54",
            section: .core,
            questionText: "Why is nitrogen gas supplied in high-pressure cylinders charged to over 2,000 psig dangerous if mishandled?",
            options: ["Nitrogen gas is highly flammable when exposed to copper", "Direct unregulated exposure can rupture HVAC equipment shells, and in enclosed spaces, nitrogen release causes instant asphyxiation", "Nitrogen destroys the ozone layer 10 times faster than chlorine", "Nitrogen reacts with moisture to form battery acid"],
            correctIndex: 1,
            explanation: "Nitrogen pressure can instantaneously burst evaporator tubes or compressor housings if connected without a regulator, and sudden discharge into confined rooms displaces all oxygen.",
            citation: "OSHA 1910 Compressed Gas Safety",
            isA2L: false
        ),
        Question(
            id: "core-55",
            section: .core,
            questionText: "When an EPA-approved refrigerant recovery cylinder has reached its maximum safe fill capacity, what percentage of the cylinder's internal volume is occupied by liquid refrigerant at 70°F?",
            options: ["60%", "75%", "80%", "95%"],
            correctIndex: 2,
            explanation: "Refillable recovery cylinders must maintain a 20% vapor cushion (filled to maximum 80% liquid volume) to absorb liquid expansion as temperature rises.",
            citation: "49 CFR § 173.304",
            isA2L: false
        ),
        Question(
            id: "core-56",
            section: .core,
            questionText: "How is the maximum safe gross weight calculated for a refrigerant recovery cylinder?",
            options: ["Gross Weight = Tare Weight + (0.8 × Water Capacity × Specific Gravity of Refrigerant)", "Gross Weight = Tare Weight + 50 lbs", "Gross Weight = Water Capacity × 1.2", "Gross Weight = Cylinder Height × 2.5"],
            correctIndex: 0,
            explanation: "The standard engineering formula mandates multiplying the cylinder water capacity (WC) by 0.8 and the refrigerant specific gravity, then adding the tare weight (TW) stamped on the collar.",
            citation: "DOT Cylinder Safety Rules",
            isA2L: false
        ),
        Question(
            id: "core-57",
            section: .core,
            questionText: "What is the purpose of the tare weight (TW) stamped on the collar of a refrigerant cylinder?",
            options: ["It indicates the weight of the cylinder when completely filled with refrigerant", "It represents the empty weight of the cylinder without any refrigerant or valve caps", "It is the test pressure at which the cylinder was proofed", "It indicates the year the cylinder was manufactured"],
            correctIndex: 1,
            explanation: "Tare weight (TW) is the weight of the empty cylinder and its attached valves. Subtracting tare weight from total measured weight yields the exact weight of refrigerant inside.",
            citation: "DOT 4BA Specification",
            isA2L: false
        ),
        Question(
            id: "core-58",
            section: .core,
            questionText: "What type of compressor oil is universally required for systems operating with HFC and HFO refrigerants?",
            options: ["Standard mineral oil (MO)", "Alkylbenzene oil (AB)", "Synthetic Polyolester oil (POE)", "Vegetable cooking oil"],
            correctIndex: 2,
            explanation: "Polyolester (POE) synthetic lubricant is miscible with polar HFC and HFO molecules, whereas non-polar mineral oils fail to return from the evaporator, causing oil starvation.",
            citation: "ASHRAE Refrigeration Handbook",
            isA2L: false
        ),
        Question(
            id: "core-59",
            section: .core,
            questionText: "What property makes POE compressor oil particularly challenging to handle during service operations?",
            options: ["It turns into a solid at room temperature", "It is highly hygroscopic, meaning it aggressively absorbs ambient moisture from the air within minutes of being uncapped", "It is radioactive", "It dissolves steel tubing"],
            correctIndex: 1,
            explanation: "POE oil is extremely hygroscopic. Leaving a bottle open for just 15 minutes allows it to absorb enough atmospheric humidity to cause system acid formation and compressor breakdown.",
            citation: "AHRI Technical Guidelines",
            isA2L: false
        ),
        Question(
            id: "core-60",
            section: .core,
            questionText: "Under the Clean Air Act, which individual is ultimately responsible for complying with refrigerant regulations during on-site servicing?",
            options: ["The equipment manufacturer", "The building security guard", "The certified technician performing the service work", "The local city mayor"],
            correctIndex: 2,
            explanation: "EPA enforcement actions and civil penalties under Section 608 hold the certified technician performing the work personally liable for regulatory non-compliance.",
            citation: "Clean Air Act § 113 / 40 CFR Part 82",
            isA2L: false
        ),
        Question(
            id: "core-61",
            section: .core,
            questionText: "What should a technician do if a cylinder of recovered refrigerant has an internal pressure significantly higher than the saturation pressure on the P-T chart at ambient temperature?",
            options: ["The cylinder contains non-condensable gases (such as air or nitrogen) and must be vented directly indoors", "The cylinder contains non-condensable gases and must be handled using recovery equipment to capture refrigerant while isolating air per AHRI guidelines", "The cylinder should be shaken vigorously to dissolve the gas", "The pressure should be ignored because P-T charts do not apply to recovery tanks"],
            correctIndex: 1,
            explanation: "High head pressure indicates air or nitrogen contamination. Non-condensables reduce heat transfer and must be purged through specialized recovery/condensing equipment to prevent refrigerant loss.",
            citation: "AHRI Guideline K",
            isA2L: false
        ),
        Question(
            id: "core-62",
            section: .core,
            questionText: "What safety classification does pure propane have under ASHRAE Standard 34 when used as refrigerant R-290?",
            options: ["A1 (non-flammable, non-toxic)", "A2L (mildly flammable)", "A3 (higher flammability, lower toxicity)", "B3 (higher flammability, higher toxicity)"],
            correctIndex: 2,
            explanation: "Propane (R-290) and isobutane (R-600a) are hydrocarbons classified as Class A3: non-toxic but highly flammable with wide explosive limits.",
            citation: "ASHRAE Standard 34",
            isA2L: true
        ),
        Question(
            id: "core-63",
            section: .core,
            questionText: "Under EPA SNAP regulations, what color markings are required on service process tubes and piping for equipment charged with flammable refrigerants (A2L or A3)?",
            options: ["Bright blue sleeves on all liquid lines", "A Pantone Matching System (PMS) #185 red marking extending at least 1 inch (2.5 cm) on all process stubs and access points", "Yellow hazard stripes on the compressor", "Green shrink wrap on the power cord"],
            correctIndex: 1,
            explanation: "EPA SNAP and UL safety standards require a red PMS #185 warning band extending at least 1 inch from all service access ports to alert technicians of flammable refrigerants.",
            citation: "EPA SNAP Rule 21 / UL Standards",
            isA2L: true
        ),
        Question(
            id: "core-64",
            section: .core,
            questionText: "What is the primary factor that causes stratospheric ozone depletion to peak during the Antarctic spring (September-October)?",
            options: ["High solar wind activity", "The formation of Polar Stratospheric Clouds (PSCs) that activate inactive chlorine into free reactive chlorine radicals under winter darkness", "Excessive commercial airline flights over Antarctica", "Volcanic eruptions in South America"],
            correctIndex: 1,
            explanation: "Polar stratospheric clouds (PSCs) provide ice crystal surfaces that convert inert chlorine reservoir molecules (HCl and ClONO2) into highly photolytic chlorine gas, which sunlight splits into destructive radicals in spring.",
            citation: "WMO / UNEP Scientific Assessment of Ozone Depletion",
            isA2L: false
        ),
        Question(
            id: "core-65",
            section: .core,
            questionText: "What is the minimum evacuation level required when using self-contained recovery equipment manufactured on or after November 15, 1993, on an appliance containing less than 200 lbs of high-pressure refrigerant?",
            options: ["0 psig (0 inches Hg vacuum)", "4 inches Hg vacuum", "10 inches Hg vacuum", "15 inches Hg vacuum"],
            correctIndex: 0,
            explanation: "Per 40 CFR § 82.156 Table 1, high-pressure appliances containing less than 200 lbs of refrigerant require 0 psig (atmospheric pressure) with post-1993 equipment.",
            citation: "40 CFR § 82.156 Table 1",
            isA2L: false
        ),
        Question(
            id: "core-66",
            section: .core,
            questionText: "What is the minimum evacuation level required when using post-1993 equipment on an appliance containing 200 or more pounds of high-pressure refrigerant (e.g. R-22)?",
            options: ["0 psig", "4 inches Hg vacuum", "10 inches Hg vacuum", "25 mm Hg absolute"],
            correctIndex: 2,
            explanation: "Per 40 CFR § 82.156 Table 1, systems containing 200 or more pounds of high-pressure refrigerant must be evacuated to 10 inches of mercury (Hg) vacuum.",
            citation: "40 CFR § 82.156 Table 1",
            isA2L: false
        ),
        Question(
            id: "core-67",
            section: .core,
            questionText: "When servicing an appliance where leaks make achieving the statutory vacuum level impossible, to what level must the system be evacuated prior to opening for repair?",
            options: ["To 15 psig pressure", "To atmospheric pressure (0 psig)", "To 25 inches Hg vacuum regardless of how much air is sucked in", "No evacuation is required if a leak is found"],
            correctIndex: 1,
            explanation: "Under 40 CFR § 82.156(a)(3), if leaks prevent pulling the target vacuum without pulling air into the recovery cylinder, the technician must evacuate the appliance to 0 psig before opening it.",
            citation: "40 CFR § 82.156(a)(3)",
            isA2L: false
        ),
        Question(
            id: "core-68",
            section: .core,
            questionText: "What does the presence of strong acid and burned odors in a recovered oil sample indicate?",
            options: ["Normal system aging", "A severe motor compressor electrical burnout that deposited corrosive hydrofluoric or hydrochloric acid throughout the circuit", "The system was charged with vegetable oil", "The expansion valve is clogged with ice"],
            correctIndex: 1,
            explanation: "Electrical arcing during motor burnout decomposes refrigerant and oil, creating strong inorganic acids. A burnout requires specialized suction and liquid cleanup filter-driers and oil flushes.",
            citation: "AHRI Service Guidelines",
            isA2L: false
        ),
        Question(
            id: "core-69",
            section: .core,
            questionText: "What test should be performed immediately after replacing a compressor that failed due to an electrical burnout?",
            options: ["A chemical oil acid test using a titration test kit", "A water conductivity test", "A spark gap test on the thermostat", "A refrigerant boil-off smell test"],
            correctIndex: 0,
            explanation: "Technicians must test the crankcase oil with an acid test kit to ensure acid levels are neutralized; unneutralized residual acid will attack and destroy the new replacement motor windings.",
            citation: "RSES Service Guidelines",
            isA2L: false
        ),
        Question(
            id: "core-70",
            section: .core,
            questionText: "What is the function of a liquid line filter-drier in a split-system refrigeration circuit?",
            options: ["To store excess liquid refrigerant during winter", "To remove particulate debris, desiccant dust, and chemical moisture from circulating liquid refrigerant", "To cool the compressor crankcase", "To prevent head pressure from rising above 200 psig"],
            correctIndex: 1,
            explanation: "Liquid line filter-driers contain molecular sieve desiccants and porous cores that capture solid contaminants and chemically bond with circulating water molecules.",
            citation: "ASHRAE Equipment Handbook",
            isA2L: false
        ),
        Question(
            id: "core-71",
            section: .core,
            questionText: "Where should a suction line filter-drier be installed following a severe compressor burnout?",
            options: ["Directly downstream of the thermal expansion valve", "Immediately upstream of the replacement compressor suction inlet", "Inside the condenser coil header", "On the discharge line before the oil separator"],
            correctIndex: 1,
            explanation: "A suction line burnout filter-drier must be mounted directly ahead of the new compressor to catch residual acid and carbon soot circulating from the evaporator before it enters the new motor.",
            citation: "Compressor Manufacturer Installation Manuals",
            isA2L: false
        ),
        Question(
            id: "core-72",
            section: .core,
            questionText: "What type of pressure relief device automatically reseats and closes after an overpressure event subsides?",
            options: ["A spring-loaded pressure relief valve", "A lead rupture disc", "A copper burst plate", "A fusible solder plug"],
            correctIndex: 0,
            explanation: "Spring-loaded relief valves automatically reseat when system pressure drops below the set point, whereas rupture discs and fusible plugs discharge the entire charge and must be replaced.",
            citation: "ASME Boiler and Pressure Vessel Code Section VIII",
            isA2L: false
        ),
        Question(
            id: "core-73",
            section: .core,
            questionText: "At what temperature do common fusible safety plugs melt to vent high pressure in water-cooled condensers or receivers?",
            options: ["120°F", "165°F to 210°F", "350°F", "500°F"],
            correctIndex: 1,
            explanation: "Fusible plugs contain a low-melting-point eutectic alloy designed to release refrigerant when temperatures reach 165°F to 210°F, protecting the vessel from hydraulic explosion during a building fire.",
            citation: "ASHRAE Standard 15",
            isA2L: false
        ),
        Question(
            id: "core-74",
            section: .core,
            questionText: "Why is it illegal to install a manual shutoff service valve in line between an operating refrigeration pressure vessel and its safety relief valve?",
            options: ["Because it restricts normal refrigerant flow to the expansion valve", "Because an accidentally closed valve leaves the vessel completely unprotected against catastrophic overpressurization and explosion", "Because the shutoff valve causes pressure drops that destroy the compressor", "Because federal law mandates all valves be motorized"],
            correctIndex: 1,
            explanation: "ASME and ASHRAE 15 strictly prohibit any intervening stop valve between a vessel and its relief device, unless a three-way diverter valve connects dual relief valves with one always open.",
            citation: "ASHRAE Standard 15 § 9.8",
            isA2L: false
        ),
        Question(
            id: "core-75",
            section: .core,
            questionText: "When dual safety relief valves are mounted on a three-way diverter valve on a large commercial receiver, how must the diverter valve be positioned?",
            options: ["Both valves must be blocked off simultaneously during operation", "The diverter must be seated so that one relief valve is open to the vessel at all times while the other can be safely serviced or replaced", "The valve must be turned to middle position to vent continuous refrigerant", "The valve must be connected directly to the building fire sprinkler system"],
            correctIndex: 1,
            explanation: "Three-way dual relief assemblies ensure that one full-capacity safety valve is always guarding the vessel while the second valve can be inspected, tested, or replaced without evacuating the system.",
            citation: "ASHRAE Standard 15 § 9.8.2",
            isA2L: false
        ),
        Question(
            id: "core-76",
            section: .core,
            questionText: "What is the primary function of a crankcase heater on a reciprocating or scroll refrigeration compressor?",
            options: ["To heat the building during winter defrost cycles", "To maintain crankcase temperature higher than the coldest part of the system, preventing refrigerant vapor from migrating and condensing into the oil during off-cycles", "To melt wax buildup on the discharge reed valves", "To boost electrical efficiency by reducing motor resistance"],
            correctIndex: 1,
            explanation: "Refrigerant naturally migrates to the coldest area of a dormant system. Keeping crankcase oil warm drives out refrigerant vapor, preventing explosive foaming and bearing failure on startup.",
            citation: "ACCA Service Manual",
            isA2L: false
        ),
        Question(
            id: "core-77",
            section: .core,
            questionText: "What happens if a compressor is energized while cold liquid refrigerant is dissolved in large quantities in the crankcase lubricant?",
            options: ["The motor runs faster and quieter", "The rapid pressure drop at startup causes violent oil foaming, washing out lubricating oil films and damaging valves and bearings (oil slugging)", "The liquid refrigerant acts as a super-lubricant that extends bearing life", "The discharge line immediately freezes over with frost"],
            correctIndex: 1,
            explanation: "Startup suction drop causes dissolved refrigerant to flash-boil violently, generating foam that enters the cylinders, ruptures discharge reeds, and strips lubrication from crankshaft bearings.",
            citation: "Compressor Reliability Engineering Bulletin",
            isA2L: false
        ),
        Question(
            id: "core-78",
            section: .core,
            questionText: "Under ASHRAE Standard 15, what equipment room feature must automatically activate upon detection of refrigerant vapor exceeding the chemical's Occupational Exposure Limit (OEL)?",
            options: ["Emergency mechanical exhaust ventilation fans and audible/visual warning alarms outside each entrance door", "Building fire sprinkler deluge systems", "Automatic emergency doors that lock all technicians inside", "The main power grid shutoff for the entire city block"],
            correctIndex: 0,
            explanation: "Machinery room vapor sensors must energize mechanical exhaust fans to purge heavier-than-air vapors outdoors and trigger audio-visual alarms at all entrances to warn approaching personnel.",
            citation: "ASHRAE Standard 15 § 8.11",
            isA2L: false
        ),
        Question(
            id: "core-79",
            section: .core,
            questionText: "What self-contained breathing apparatus (SCBA) requirement applies when entering a machinery room with an unknown concentration of released refrigerant?",
            options: ["A cotton face mask soaked in water", "A positive-pressure, full-facepiece NIOSH-certified Self-Contained Breathing Apparatus (SCBA) or supplied-air respirator", "A half-mask dust respirator with organic vapor cartridges", "No breathing equipment is needed if the room lights are functional"],
            correctIndex: 1,
            explanation: "Cartridge respirators cannot protect against oxygen deficiency caused by heavy refrigerant displacement. Only positive-pressure supplied-air SCBA gear provides life support.",
            citation: "OSHA 1910.134 Respiratory Protection",
            isA2L: false
        ),
        Question(
            id: "core-80",
            section: .core,
            questionText: "What test is commonly used to detect minute leaks around valve stems, flared fittings, and brazed joints when electronic detectors cannot be maneuvered?",
            options: ["A lighted match or open butane lighter flame", "An approved halide-free soap bubble leak detection solution", "Pouring motor oil directly onto the joint to look for smoke", "Listening for whistling sounds with an ear pressed against the pipe"],
            correctIndex: 1,
            explanation: "Specialized high-viscosity bubble solutions form persistent micro-bubbles over pinpoint leaks and do not contain corrosive chlorides that crack brass or copper fittings.",
            citation: "ACCA Field Practice Manual",
            isA2L: false
        ),
        Question(
            id: "core-81",
            section: .core,
            questionText: "Why should open flames (such as halide torches) NEVER be used to leak check modern HFC or A2L refrigerant systems?",
            options: ["The flame will melt the copper fittings instantly", "Halide torches only react with chlorine (which HFCs lack) and open flames can ignite flammable A2L/A3 vapors while generating deadly toxic phosgene gases", "The torch consumes all oxygen in the entire building within seconds", "Halide torches ruin the calibration of digital thermometers"],
            correctIndex: 1,
            explanation: "Halide torches detect chlorine only; they are useless on fluorine-only HFCs/HFOs, and bringing an open flame near A2L/A3 systems introduces a direct source of fire and toxic acid gas.",
            citation: "EPA Section 608 Safety Directives",
            isA2L: true
        ),
        Question(
            id: "core-82",
            section: .core,
            questionText: "Which electronic leak detector technology is most effective at locating pinpoint leaks in systems charged with HFO and A2L refrigerants?",
            options: ["Infrared (IR) optical sensors or heated diode detectors", "Standard acoustic stethoscope", "Ultraviolet dye lights without any electronic sensor", "Carbon dioxide sniffing probes"],
            correctIndex: 0,
            explanation: "Infrared and advanced heated-diode sensors respond selectively to the molecular absorption bands of fluorinated refrigerants without false-alarming on moisture, dirt, or oil fumes.",
            citation: "AHRI Testing Protocols",
            isA2L: true
        ),
        Question(
            id: "core-83",
            section: .core,
            questionText: "When adding fluorescent ultraviolet (UV) leak detection dye to a refrigeration system, what specification must be verified?",
            options: ["The dye must be approved by the compressor manufacturer for compatibility with the specific oil (POE) and refrigerant blend", "The dye must be bright red in visible daylight", "The dye must contain at least 10% water to dissolve properly", "The dye must be injected into the high-pressure discharge port while running"],
            correctIndex: 0,
            explanation: "Non-approved dyes can compromise lubricant dielectric strength, attack motor insulation, or void OEM compressor warranties.",
            citation: "OEM Compressor Service Bulletins",
            isA2L: false
        ),
        Question(
            id: "core-84",
            section: .core,
            questionText: "Under 40 CFR § 82.161, how long does an EPA Section 608 technician certification remain valid once earned?",
            options: ["For 1 year", "For 5 years", "For 10 years", "It is valid for the technician's lifetime and does not expire"],
            correctIndex: 3,
            explanation: "Currently, EPA Section 608 technician certification credentials do not expire and do not require periodic renewal testing under federal law.",
            citation: "40 CFR § 82.161",
            isA2L: false
        ),
        Question(
            id: "core-85",
            section: .core,
            questionText: "What should a certified technician do if their original EPA 608 certification wallet card is lost or destroyed?",
            options: ["Retake the entire exam from scratch immediately", "Contact the EPA-approved testing organization that issued the original credential to obtain a certified replacement card", "Write a self-attested certificate on company letterhead", "Technicians are barred from working in the trade for 6 months"],
            correctIndex: 1,
            explanation: "Technicians must contact their original certifying testing organization (e.g. Esco Institute, Ferris State, Mainstream Engineering) to verify test records and issue a replacement.",
            citation: "EPA Section 608 FAQ Guidelines",
            isA2L: false
        ),
        Question(
            id: "core-86",
            section: .core,
            questionText: "What is the primary environmental impact of releasing Hydrofluorocarbons (HFCs) into the atmosphere?",
            options: ["Direct destruction of the stratospheric ozone layer", "Intense global warming and climate change due to their high greenhouse gas heat-trapping capacity", "Formation of acid rain that defoliates forests", "Depletion of ocean salinity"],
            correctIndex: 1,
            explanation: "HFCs contain no chlorine and have zero ODP, but their global warming potential (GWP) is thousands of times greater than CO2, trapping atmospheric heat.",
            citation: "EPA AIM Act Environmental Finding",
            isA2L: false
        ),
        Question(
            id: "core-87",
            section: .core,
            questionText: "Which of the following compounds has the HIGHEST Ozone Depletion Potential (ODP)?",
            options: ["CFC-12", "HCFC-22", "HFC-134a", "HFO-1234yf"],
            correctIndex: 0,
            explanation: "CFC-12 has an ODP of 1.0 (tied with CFC-11). HCFC-22 has an ODP of 0.055, while HFC-134a and HFO-1234yf have zero ODP.",
            citation: "40 CFR Part 82 Subpart A",
            isA2L: false
        ),
        Question(
            id: "core-88",
            section: .core,
            questionText: "Under the Clean Air Act, what reward does the EPA offer to individuals who provide information leading to a criminal conviction for illegal refrigerant venting?",
            options: ["Up to $1,000", "Up to $10,000", "Up to $25,000", "There are no rewards under federal law"],
            correctIndex: 1,
            explanation: "Clean Air Act Section 113(f) authorizes the EPA Administrator to pay an award of up to $10,000 to any person who furnishes information leading to a criminal conviction or civil penalty.",
            citation: "Clean Air Act § 113(f)",
            isA2L: false
        ),
        Question(
            id: "core-89",
            section: .core,
            questionText: "Why does the EPA require recovery machines to be equipped with low-loss shutoff valves at the hose connection ends?",
            options: ["To prevent excessive pressure buildup in the recovery machine", "To minimize refrigerant trapping in the charging hose and prevent emissions during connection and disconnection", "To keep the technician's hands clean from compressor oil", "To increase the speed of refrigerant transfer by 50%"],
            correctIndex: 1,
            explanation: "Hoses without shutoff valves vent several ounces of trapped high-pressure liquid upon disconnection, which violates EPA emission reduction rules.",
            citation: "40 CFR § 82.158",
            isA2L: false
        ),
        Question(
            id: "core-90",
            section: .core,
            questionText: "When recovering vapor from an HVAC unit, what simple step can a technician take to significantly reduce recovery time?",
            options: ["Connect the recovery cylinder to an open fire hydrant", "Pack the recovery cylinder in ice or a cold water bath to lower tank pressure and increase the pressure differential", "Heat the recovery machine compressor with a blowtorch", "Open the manifold gauge low-side handwheel only halfway"],
            correctIndex: 1,
            explanation: "Cooling the recovery cylinder lowers internal vapor saturation pressure, allowing the recovery compressor to pump against lower head pressure and move vapor much faster.",
            citation: "AHRI Recovery Practice Guide",
            isA2L: false
        ),
        Question(
            id: "core-91",
            section: .core,
            questionText: "What happens if a technician recovers refrigerant into a cylinder that already contains an unknown different refrigerant?",
            options: ["The two refrigerants will separate into two distinct layers that can be drained individually", "The cylinder contents become a contaminated chemical cocktail that no reclaimer can separate, requiring expensive hazardous waste incineration", "The cylinder pressure will instantly drop to zero", "The recovery machine compressor motor will run backward"],
            correctIndex: 1,
            explanation: "Reclaimers can only separate oil and moisture from single compounds or standard blends. Mixed refrigerants cannot be fractionated back to purity and are sent to hazardous incinerators.",
            citation: "EPA Reclaiming Standards",
            isA2L: false
        ),
        Question(
            id: "core-92",
            section: .core,
            questionText: "Under 40 CFR § 82.156, what is the maximum allowable pressure drop during a standing nitrogen pressure hold test to prove leak tightness before evacuating?",
            options: ["The system must hold target pressure (e.g. 150 psig) with zero measurable pressure drop after temperature stabilization for the specified test duration", "A drop of 10 psig per hour is acceptable", "Pressure may drop to zero as long as no bubbles were seen", "A 20% pressure drop is standard for all residential systems"],
            correctIndex: 0,
            explanation: "A sound system must maintain constant pressure during a standing test once temperature equalizes. Any steady pressure decline indicates an ongoing leak that must be repaired before charging.",
            citation: "ACCA Quality Installation Standards",
            isA2L: false
        ),
        Question(
            id: "core-93",
            section: .core,
            questionText: "Why must manifold gauge hoses be as short in length and as large in diameter as practical during deep evacuation?",
            options: ["To keep the service vehicle floor organized", "To reduce friction resistance and dramatically shorten evacuation time to reach deep vacuum levels (500 microns)", "To prevent the vacuum pump oil from overheating", "Because long hoses cause refrigerant to explode"],
            correctIndex: 1,
            explanation: "Molecular flow under deep vacuum is severely throttled by hose friction. Using short 3/8\" or 1/2\" vacuum-rated hoses cuts evacuation time by up to 75% compared to 1/4\" charging hoses.",
            citation: "HVAC Dehydration Field Handbook",
            isA2L: false
        ),
        Question(
            id: "core-94",
            section: .core,
            questionText: "What is the primary function of removing Schrader valve cores using a core removal tool prior to evacuating an HVAC system?",
            options: ["To allow oil to drain into the recovery cylinder", "To eliminate the severe restriction caused by the tiny spring-loaded valve core, increasing evacuation flow rate up to 10-fold", "To prevent the Schrader rubber seal from melting under vacuum", "To allow atmospheric air to enter during recovery"],
            correctIndex: 1,
            explanation: "Schrader valve depressors create tremendous flow friction. Removing cores with vacuum-rated tools allows unrestricted molecular conductance into the vacuum pump.",
            citation: "Fieldpiece Evacuation Best Practices",
            isA2L: false
        ),
        Question(
            id: "core-95",
            section: .core,
            questionText: "Under the EPA SNAP program, what does the designation 'SNAP' stand for?",
            options: ["Standard Nitrogen Application Protocol", "Significant New Alternatives Policy", "Systematic Non-ozone Action Program", "Stratospheric Nitrogen Assessment Plan"],
            correctIndex: 1,
            explanation: "SNAP stands for the Significant New Alternatives Policy program, which evaluates and lists acceptable and unacceptable chemical substitutes for ozone-depleting substances.",
            citation: "Clean Air Act § 612 / 40 CFR Part 82 Subpart G",
            isA2L: false
        ),
        Question(
            id: "core-96",
            section: .core,
            questionText: "Which of the following refrigerants is classified as an inorganic natural refrigerant with zero ODP and a GWP of zero?",
            options: ["R-717 (Ammonia)", "R-134a", "R-410A", "R-407C"],
            correctIndex: 0,
            explanation: "Ammonia (R-717) is an inorganic natural refrigerant consisting of nitrogen and hydrogen (NH3), with an ODP of zero and a GWP of zero.",
            citation: "ASHRAE Standard 34",
            isA2L: false
        ),
        Question(
            id: "core-97",
            section: .core,
            questionText: "What classification does Ammonia (R-717) hold under ASHRAE Standard 34?",
            options: ["A1", "A2L", "B2L", "B3"],
            correctIndex: 2,
            explanation: "Ammonia is classified as B2L: higher toxicity (Group B) due to caustic inhalation risks, and lower flammability (Class 2L) with burning velocity <= 10 cm/s.",
            citation: "ASHRAE Standard 34",
            isA2L: false
        ),
        Question(
            id: "core-98",
            section: .core,
            questionText: "Why must technicians change vacuum pump oil regularly after servicing wet or contaminated refrigeration systems?",
            options: ["Because dirty oil makes the vacuum pump motor spin too fast", "Because water vapor pulled from systems condenses inside the vacuum pump crankcase, raising pump blank-off pressure and preventing deep vacuum below 1,000 microns", "Because vacuum pump oil turns into refrigerant vapor after two uses", "Because EPA rules mandate recycling vacuum pump oil every 2 hours"],
            correctIndex: 1,
            explanation: "Water contaminating vacuum pump oil prevents the pump from pulling below the vapor pressure of water (~24,000 microns at room temperature). Fresh oil is required to achieve 500 microns.",
            citation: "Vacuum Technology Handbook",
            isA2L: false
        ),
        Question(
            id: "core-99",
            section: .core,
            questionText: "What is the proper method for disposing of empty disposable (DOT-39) refrigerant cylinders?",
            options: ["Throw them directly into a dumpster while still containing 10 psig of vapor", "Evacuate remaining vapor to 0 psig, depressurize the cylinder completely, render it unusable by puncturing or breaking off the valve stem, and recycle as scrap metal", "Refill them with compressed dry nitrogen for pressure testing", "Incinerate them in an open burn barrel"],
            correctIndex: 1,
            explanation: "EPA regulations require recovering all residual refrigerant to 0 psig before recycling DOT-39 cylinders. The cylinder must be physically punctured or crushed so it cannot be reused.",
            citation: "40 CFR § 82.156(f) / DOT Guidelines",
            isA2L: false
        ),
        Question(
            id: "core-100",
            section: .core,
            questionText: "What federal document must be consulted to determine the specific toxic exposure limits, emergency first aid, and physical properties of a newly introduced A2L refrigerant?",
            options: ["The local phone directory", "The manufacturer's Safety Data Sheet (SDS)", "The vehicle owner's manual", "The city building permit application"],
            correctIndex: 1,
            explanation: "The Safety Data Sheet (SDS) contains essential chemical information including OSHA permissible exposure limits (PEL), Lower Flammability Limits (LFL), and specific emergency procedures.",
            citation: "OSHA Hazard Communication Standard 29 CFR 1910.1200",
            isA2L: true
        )
    ]
}
