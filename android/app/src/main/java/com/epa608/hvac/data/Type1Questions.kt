package com.epa608.hvac.data

import com.epa608.hvac.domain.EPASection
import com.epa608.hvac.domain.Question

object Type1Questions {
    val list: List<Question> = listOf(
        Question(
            id = "t1-1",
            section = EPASection.TYPE_I,
            questionText = "What is the maximum charge size of factory-sealed refrigerant for an appliance to be categorized as a Type I Small Appliance?",
            options = listOf("2 pounds", "5 pounds", "10 pounds", "15 pounds"),
            correctIndex = 1,
            explanation = "EPA Section 608 defines a Type I Small Appliance as a system that is manufactured, charged, and hermetically sealed in a factory with five (5) pounds or less of refrigerant.",
            citation = "40 CFR § 82.152",
            isA2L = false
        ),
        Question(
            id = "t1-2",
            section = EPASection.TYPE_I,
            questionText = "When recovering refrigerant from a household refrigerator using a system-dependent (passive) recovery process on an appliance with an operating compressor, where should the access fitting be placed?",
            options = listOf("Only on the suction process tube", "On both the high side and the low side", "Only on the high side (discharge side) of the compressor", "Directly into the evaporator coil"),
            correctIndex = 2,
            explanation = "With an operating compressor in a system-dependent process, access to the high side is typically sufficient because the appliance's own compressor forces refrigerant into the recovery bag or cylinder.",
            citation = "40 CFR § 82.156(a)",
            isA2L = false
        ),
        Question(
            id = "t1-3",
            section = EPASection.TYPE_I,
            questionText = "What is the maximum allowable factory charge limit for flammable hydrocarbon refrigerants (such as R-600a isobutane or R-290 propane) in new domestic refrigerators?",
            options = listOf("50 grams", "150 grams (approx. 5.3 oz)", "500 grams", "2 pounds"),
            correctIndex = 1,
            explanation = "EPA SNAP regulations and UL standards cap domestic small appliance charges for flammable hydrocarbon refrigerants at 150 grams (5.3 oz) to prevent explosive concentrations in the event of an indoor leak.",
            citation = "EPA SNAP Rule 21 / UL 60335-2-24",
            isA2L = true
        ),
        Question(
            id = "t1-4",
            section = EPASection.TYPE_I,
            questionText = "If a small appliance has a completely inoperative compressor and a technician is using a system-dependent recovery process, what access configuration is legally required?",
            options = listOf("Access to the high side only", "Access to both the high side and the low side of the system", "No recovery is required if the compressor does not run", "Access directly through the defrost drain pan"),
            correctIndex = 1,
            explanation = "Under 40 CFR § 82.156(a), if the compressor is inoperative during passive recovery, accessing both the high and low sides is mandatory to achieve the statutory 80% recovery requirement.",
            citation = "40 CFR § 82.156(a)",
            isA2L = false
        ),
        Question(
            id = "t1-5",
            section = EPASection.TYPE_I,
            questionText = "How can non-condensable gases (such as air) be identified in a sealed recovery cylinder containing recovered R-134a at room temperature?",
            options = listOf("By weighing the cylinder on a digital scale", "By measuring cylinder pressure and comparing it to the saturation pressure on a P-T chart at ambient temperature", "By smelling the vapor discharge port", "By shaking the cylinder to listen for liquid sloshing"),
            correctIndex = 1,
            explanation = "Air and moisture in a cylinder increase total head pressure above saturation. If the cylinder pressure is higher than the P-T chart pressure for the measured ambient temperature, non-condensable gas is present.",
            citation = "AHRI Guideline K / EPA 608",
            isA2L = false
        ),
        Question(
            id = "t1-6",
            section = EPASection.TYPE_I,
            questionText = "When using an active (self-contained) recovery unit on a small appliance with an OPERATING compressor, what percentage of refrigerant must be recovered under 40 CFR § 82.156(a)(2)?",
            options = listOf("70% of the nameplate charge", "80% of the nameplate charge", "90% of the nameplate charge", "100% with no remaining trace"),
            correctIndex = 2,
            explanation = "Under 40 CFR § 82.156(a)(2), self-contained recovery equipment must recover 90% of the refrigerant charge when the appliance compressor is operating.",
            citation = "40 CFR § 82.156(a)(2)",
            isA2L = false
        ),
        Question(
            id = "t1-7",
            section = EPASection.TYPE_I,
            questionText = "When using an active (self-contained) recovery unit on a small appliance with an INOPERATIVE compressor, what statutory standard must be met?",
            options = listOf("Recover at least 80% of the nameplate charge, or achieve a 4-inch Hg vacuum", "Recover at least 50% of the nameplate charge", "Vent remaining vapor because the compressor cannot pump", "Pull a 25-inch Hg vacuum in under 30 seconds"),
            correctIndex = 0,
            explanation = "If the appliance compressor does not function, active recovery equipment must achieve an 80% recovery level or pull a 4-inch mercury vacuum.",
            citation = "40 CFR § 82.156(a)(2)",
            isA2L = false
        ),
        Question(
            id = "t1-8",
            section = EPASection.TYPE_I,
            questionText = "Why are solderless piercing (saddle) valves considered temporary service fittings that must be removed after repair?",
            options = listOf("Because their brass body cracks after two weeks", "Because their internal rubber/neoprene sealing gaskets deteriorate over time and develop chronic leaks", "Because they alter the electrical capacitance of the starting relay", "Because EPA regulations prohibit piercing valves from ever being used on any equipment"),
            correctIndex = 1,
            explanation = "Piercing valves rely on elastomeric gaskets that vulcanize, dry out, and leak under temperature cycling. They must be removed, brazed over, or replaced with permanent access fittings.",
            citation = "EPA Section 608 Type I Guidelines",
            isA2L = false
        ),
        Question(
            id = "t1-9",
            section = EPASection.TYPE_I,
            questionText = "What is the primary method for freeing trapped refrigerant from compressor oil when servicing an appliance with an inoperative compressor?",
            options = listOf("Striking the compressor with an iron sledgehammer", "Applying gentle heat with an electric heating blanket or hot air gun to the crankcase, and gently tapping with a rubber mallet", "Pouring liquid nitrogen over the electrical terminals", "Drilling a 1/2-inch hole into the compressor housing"),
            correctIndex = 1,
            explanation = "Refrigerant dissolves deeply into cold compressor oil. Warming the compressor housing and gently tapping with a mallet boils out dissolved refrigerant vapor so it can be recovered.",
            citation = "AHRI Small Appliance Service Guide",
            isA2L = false
        ),
        Question(
            id = "t1-10",
            section = EPASection.TYPE_I,
            questionText = "Under 40 CFR § 82.156(f), who bears the legal responsibility for ensuring that refrigerant has been recovered from small appliances before final scrap disposal?",
            options = listOf("The homeowner who purchased the refrigerator originally", "The final person in the disposal chain (such as the scrap metal recycler or landfill operator)", "The manufacturer who built the appliance 20 years ago", "The city municipal electric utility company"),
            correctIndex = 1,
            explanation = "Federal law mandates that the final person in the disposal chain must either recover the refrigerant or verify through signed documentation that recovery was completed prior to delivery.",
            citation = "40 CFR § 82.156(f)",
            isA2L = false
        ),
        Question(
            id = "t1-11",
            section = EPASection.TYPE_I,
            questionText = "What written documentation is required when delivering small appliances to a scrap metal recycler where refrigerant was previously evacuated by a technician?",
            options = listOf("A copy of the technician's high school diploma", "A signed statement containing the technician's name, address, certification number, date, and verification that all refrigerant was recovered", "A handwritten note on a cardboard box", "No documentation is required if the appliance power cord is cut off"),
            correctIndex = 1,
            explanation = "The final disposal documentation must include the name and address of the person who recovered the refrigerant, the date of recovery, and the certification status.",
            citation = "40 CFR § 82.156(f)(2)",
            isA2L = false
        ),
        Question(
            id = "t1-12",
            section = EPASection.TYPE_I,
            questionText = "Which of the following equipment types is specifically classified as a Type I Small Appliance under EPA regulations?",
            options = listOf("A 10-ton rooftop air handler", "A packaged terminal heat pump (PTHP) charged with 3.5 lbs of R-410A", "A supermarket ice cream display rack with 150 lbs of R-404A", "A 100-ton centrifugal water chiller"),
            correctIndex = 1,
            explanation = "A PTHP factory-sealed with under 5 pounds of refrigerant is hermetically sealed at the factory and meets the strict definition of a Type I small appliance.",
            citation = "40 CFR § 82.152",
            isA2L = false
        ),
        Question(
            id = "t1-13",
            section = EPASection.TYPE_I,
            questionText = "What type of recovery system utilizes the small appliance's own internal compressor and pressure to move refrigerant into an evacuated cylinder?",
            options = listOf("A self-contained active recovery system", "A system-dependent (passive) recovery system", "A cryogenic vacuum pump system", "An atmospheric venting manifold"),
            correctIndex = 1,
            explanation = "System-dependent (passive) recovery relies entirely on the internal pressure of the appliance, external heat, or the appliance's own compressor to push refrigerant into a non-pressurized recovery container.",
            citation = "40 CFR § 82.152",
            isA2L = false
        ),
        Question(
            id = "t1-14",
            section = EPASection.TYPE_I,
            questionText = "When performing system-dependent recovery on a domestic freezer with an inoperative compressor, why must both high and low process stubs be accessed?",
            options = listOf("To prevent the oil sight glass from clouding", "Because high-side and low-side pressures will not equalize through the capillary tube restriction within a reasonable recovery time", "Because passive recovery machines require two separate power supplies", "To allow atmospheric nitrogen to enter the evaporator"),
            correctIndex = 1,
            explanation = "With a dead compressor, tiny capillary tube or thermal expansion valve restrictions impede refrigerant flow. Accessing both sides ensures complete recovery from both the evaporator and condenser.",
            citation = "EPA Type I Certification Manual",
            isA2L = false
        ),
        Question(
            id = "t1-15",
            section = EPASection.TYPE_I,
            questionText = "Under what condition is it acceptable to vent the small quantity of gas released when connecting or disconnecting access hoses with low-loss fittings?",
            options = listOf("Only when the ambient humidity is below 30%", "Whenever the release is an unavoidable 'de-minimis' consequence of good-faith service practices", "Never; all hoses must be permanently welded to the appliance", "Only if the technician is working outdoors in a public park"),
            correctIndex = 1,
            explanation = "The Clean Air Act exempts de-minimis releases associated with good-faith attempts to capture refrigerant using low-loss fittings.",
            citation = "40 CFR § 82.154(a)(2)",
            isA2L = false
        ),
        Question(
            id = "t1-16",
            section = EPASection.TYPE_I,
            questionText = "What safety precaution must be taken before brazing a new process tube onto a small appliance charged with R-600a (isobutane)?",
            options = listOf("The appliance must be plugged in and running", "All flammable hydrocarbon refrigerant must be fully evacuated, and the lines purged with dry nitrogen before using an open flame torch", "The technician must pour motor oil over the brazing joint", "No precaution is necessary because R-600a will not burn in copper lines"),
            correctIndex = 1,
            explanation = "R-600a is an A3 flammable hydrocarbon. Brazing near residual hydrocarbon vapor will cause flash fires or explosive flame propagation; nitrogen purging is mandatory.",
            citation = "UL 60335-2-24 Safety Rules",
            isA2L = true
        ),
        Question(
            id = "t1-17",
            section = EPASection.TYPE_I,
            questionText = "What is the characteristic color code marking required on process tubes for domestic appliances using flammable refrigerants like R-600a or R-290?",
            options = listOf("A yellow band around the thermostat", "A bright red PMS #185 marking at least 1 inch wide on all service access points", "A solid green compressor casing", "A blue dot on the power plug"),
            correctIndex = 1,
            explanation = "Red color coding is federally mandated on service tubes to instantly alert field technicians that the system contains flammable refrigerant.",
            citation = "EPA SNAP Rule 21",
            isA2L = true
        ),
        Question(
            id = "t1-18",
            section = EPASection.TYPE_I,
            questionText = "What type of access valve is typically installed on factory hermetic small appliances at the factory?",
            options = listOf("Manual 3-way brass service valves with square stems", "A straight copper process tube (process stub) that is pinched off and brazed shut", "Schrader core fittings with automotive caps", "Flanged quick-disconnect couplings"),
            correctIndex = 1,
            explanation = "Domestic refrigerators have no service valves. Manufacturers braze a crimped process stub directly to the compressor to minimize potential leak paths.",
            citation = "AHAM Small Appliance Standards",
            isA2L = false
        ),
        Question(
            id = "t1-19",
            section = EPASection.TYPE_I,
            questionText = "When using a piercing valve to access a small appliance process stub, what must be done to the copper tubing surface beforehand?",
            options = listOf("The tubing must be painted red", "The copper surface must be cleaned with emery cloth or sandpaper to remove corrosion, paint, and dirt to ensure a leak-tight seal", "The tubing must be heated with a torch until red hot", "The copper must be covered with Teflon tape"),
            correctIndex = 1,
            explanation = "A clean, burr-free surface ensures the rubber gasket forms an airtight seal and prevents dirt particles from entering the refrigeration circuit upon piercing.",
            citation = "RSES Service Practices",
            isA2L = false
        ),
        Question(
            id = "t1-20",
            section = EPASection.TYPE_I,
            questionText = "What is the typical result of connecting an active recovery unit to an appliance through open-ended hoses without low-loss shutoff devices?",
            options = listOf("The recovery unit pulls a deeper vacuum", "Substantial amounts of high-pressure liquid refrigerant will escape into the atmosphere upon hose disconnection, violating federal law", "The compressor oil turns to foam", "The recovery machine electric motor burns out"),
            correctIndex = 1,
            explanation = "Low-loss fittings or manual valves are legally mandated to prevent venting several ounces of liquid trapped in service hoses.",
            citation = "40 CFR § 82.158",
            isA2L = false
        ),
        Question(
            id = "t1-21",
            section = EPASection.TYPE_I,
            questionText = "What should be done if a sealed recovery cylinder containing recovered R-134a has a pressure of 115 psig at an ambient temperature of 75°F (where saturation pressure is ~78 psig)?",
            options = listOf("The cylinder has excess non-condensable gases (air) that must be isolated and purged per AHRI Guideline K", "The cylinder should be placed in an oven to equalize pressure", "The technician should immediately vent the liquid from the bottom valve", "The cylinder pressure is normal for R-134a"),
            correctIndex = 0,
            explanation = "A measured pressure higher than the saturation temperature indicates air contamination. Non-condensables raise head pressure and must be purged according to AHRI guidelines.",
            citation = "AHRI Guideline K",
            isA2L = false
        ),
        Question(
            id = "t1-22",
            section = EPASection.TYPE_I,
            questionText = "Under what circumstances is an HVAC technician permitted to use a passive recovery device on a small appliance?",
            options = listOf("Only on commercial rooftop air conditioning units", "When servicing Type I small appliances in residences or shops", "On low-pressure centrifugal water chillers", "Never; passive recovery is completely outlawed by the EPA"),
            correctIndex = 1,
            explanation = "Passive (system-dependent) recovery is permitted under 40 CFR § 82.156(a) ONLY for small appliances containing 5 lbs or less of refrigerant.",
            citation = "40 CFR § 82.156(a)",
            isA2L = false
        ),
        Question(
            id = "t1-23",
            section = EPASection.TYPE_I,
            questionText = "What happens to the refrigerant inside an inoperative small appliance compressor when the crankcase cools down overnight?",
            options = listOf("The refrigerant escapes into the atmosphere", "The refrigerant condenses and migrates into the compressor oil sump, becoming deeply dissolved in the lubricant", "The refrigerant turns into solid carbon crystals", "The refrigerant separates into oxygen and hydrogen"),
            correctIndex = 1,
            explanation = "Refrigerant vapors have a high chemical affinity for lubricating oil and dissolve into the oil at lower temperatures.",
            citation = "ASHRAE Fundamentals Handbook",
            isA2L = false
        ),
        Question(
            id = "t1-24",
            section = EPASection.TYPE_I,
            questionText = "Why is an electronic leak detector sometimes unable to detect leaks in a domestic refrigerator that has been turned off for weeks?",
            options = listOf("Because the copper pipes become magnetic", "Because high-side and low-side pressures equalize, and low-side pressure may remain too low or in a partial vacuum to force vapor through small pinholes", "Because refrigerant decomposes within 24 hours of shutoff", "Because the electronic detector only works on moving vapor"),
            correctIndex = 1,
            explanation = "Low internal pressure reduces vapor discharge through leaks. Technicians often pressurize with nitrogen to 50-100 psig to pinpoint small leaks.",
            citation = "EPA 608 Field Diagnostic Guide",
            isA2L = false
        ),
        Question(
            id = "t1-25",
            section = EPASection.TYPE_I,
            questionText = "What is the maximum pressure to which a small appliance refrigeration system should be pressurized with dry nitrogen during leak checks?",
            options = listOf("Never exceed the low-side design test pressure stamped on the appliance nameplate (typically 150 psig)", "500 psig to guarantee pinhole opening", "1,000 psig", "No pressure is allowed under federal law"),
            correctIndex = 0,
            explanation = "Exceeding the nameplate low-side design test pressure can rupture thin evaporator walls, defrost plates, or aluminum tubing.",
            citation = "ACCA Quality Standards",
            isA2L = false
        ),
        Question(
            id = "t1-26",
            section = EPASection.TYPE_I,
            questionText = "What safety risk is created if a technician uses an oxy-acetylene torch to melt solder on an unvented, sealed compressor housing?",
            options = listOf("The flame will generate pure oxygen", "Trapped refrigerant and oil vapor will expand rapidly, creating extreme hydraulic pressure that can rupture the compressor shell explosively", "The copper tubing will turn to gold", "The refrigerant will lose its thermodynamic properties"),
            correctIndex = 1,
            explanation = "Heating a sealed container causes liquid and oil vapor to flash-boil, creating explosive internal pressures that burst the steel shell.",
            citation = "OSHA 1910 Hot Work Safety",
            isA2L = false
        ),
        Question(
            id = "t1-27",
            section = EPASection.TYPE_I,
            questionText = "When recovering refrigerant from a small appliance into a non-pressurized plastic recovery bag, how is the refrigerant captured?",
            options = listOf("By chemical reaction with salt inside the bag", "By the appliance compressor pumping refrigerant vapor into the bag until it inflates at atmospheric pressure", "By vacuum created from ice cubes in the bag", "By an electric fan mounted inside the bag"),
            correctIndex = 1,
            explanation = "Non-pressurized bags operate on system pressure or the appliance compressor pumping vapor into an evacuated bladder until ambient pressure is reached.",
            citation = "EPA Type I Equipment Guidelines",
            isA2L = false
        ),
        Question(
            id = "t1-28",
            section = EPASection.TYPE_I,
            questionText = "Under 40 CFR § 82.158, what is the recovery certification standard for small appliance recovery equipment manufactured BEFORE November 15, 1993?",
            options = listOf("Must recover 80% of refrigerant whether compressor runs or not, or pull a 4-inch Hg vacuum", "Must recover 99% of refrigerant", "Must achieve a 25-inch Hg vacuum", "Pre-1993 equipment is exempt from all recovery standards"),
            correctIndex = 0,
            explanation = "Older recovery machines manufactured before Nov 15, 1993, must achieve an 80% recovery level or 4 inches of Hg vacuum.",
            citation = "40 CFR § 82.158 Table 2",
            isA2L = false
        ),
        Question(
            id = "t1-29",
            section = EPASection.TYPE_I,
            questionText = "What is the primary indicator that a small appliance has a completely blocked capillary tube restriction?",
            options = listOf("The compressor runs continuously, the evaporator remains warm, and low-side pressure drops into a deep vacuum while high-side pressure is elevated", "The evaporator coil is completely encased in 6 inches of solid ice", "The compressor motor spins backward", "The power consumption drops to zero watts"),
            correctIndex = 0,
            explanation = "A plugged restriction prevents refrigerant flow into the evaporator; the compressor pumps all remaining low-side vapor into the condenser, pulling the suction into deep vacuum.",
            citation = "RSES Troubleshooting Guide",
            isA2L = false
        ),
        Question(
            id = "t1-30",
            section = EPASection.TYPE_I,
            questionText = "Why must technicians avoid running a hermetic small appliance compressor while pulling a deep vacuum on the system?",
            options = listOf("Because the compressor will consume too much electricity", "Because hermetic motor windings rely on circulating refrigerant vapor for cooling and motor electrical insulation dielectric strength degrades under vacuum, risking arcing and motor burnout", "Because vacuum pulls all copper plating off the tubing", "Because the compressor will spin at double speed"),
            correctIndex = 1,
            explanation = "Air and refrigerant vapor provide electrical insulation across open motor terminals (Paschen's Law). Energizing a compressor in a vacuum causes electrical arcing and burns out the motor.",
            citation = "Tecumseh Hermetic Compressor Guidelines",
            isA2L = false
        ),
        Question(
            id = "t1-31",
            section = EPASection.TYPE_I,
            questionText = "What type of recovery machine is defined as being independent of the components in the appliance, containing its own motor and compressor?",
            options = listOf("A system-dependent recovery unit", "A self-contained (active) recovery unit", "A thermosiphon recovery loop", "A capillary recovery chamber"),
            correctIndex = 1,
            explanation = "Active recovery machines have their own compressor and condensing circuit, allowing recovery regardless of whether the appliance compressor functions.",
            citation = "40 CFR § 82.152",
            isA2L = false
        ),
        Question(
            id = "t1-32",
            section = EPASection.TYPE_I,
            questionText = "When servicing a small water cooler with a charge of 8 ounces of R-134a, what access tool should be used if no factory process ports exist?",
            options = listOf("A high-pressure ball valve threaded into the condenser", "A solderless clamp-on piercing valve attached to the process tube", "An oxy-acetylene cutting torch", "A cold chisel and hammer"),
            correctIndex = 1,
            explanation = "A temporary clamp-on piercing valve is installed on the hermetic process tube to establish access for recovery before brazing permanent fittings.",
            citation = "EPA Type I Service Guidelines",
            isA2L = false
        ),
        Question(
            id = "t1-33",
            section = EPASection.TYPE_I,
            questionText = "Why should a piercing valve NOT be left permanently on an appliance after service is completed?",
            options = listOf("The weight of the valve bends copper tubing over time", "The valve gasket seal will degrade under heat and vibration, releasing refrigerant into the atmosphere", "The valve restricts the voltage supplied to the compressor", "Federal law requires all brass valves to be recycled within 48 hours"),
            correctIndex = 1,
            explanation = "Elastomeric seals in piercing valves dry out, crack, and fail, causing refrigerant leaks. Permanent braze-on process stubs or Schrader valves must be installed.",
            citation = "EPA Section 608 Policy",
            isA2L = false
        ),
        Question(
            id = "t1-34",
            section = EPASection.TYPE_I,
            questionText = "What safety check must be performed on a recovery cylinder before connecting it to a small appliance recovery unit?",
            options = listOf("Check that the cylinder is painted solid black", "Verify the tare weight, check hydrostatic test date, and check current weight to ensure it is not filled beyond 80% capacity", "Heat the cylinder with a heat gun to 150°F", "Invert the cylinder and drain 2 quarts of oil onto the ground"),
            correctIndex = 1,
            explanation = "Technicians must verify the cylinder is within its 5-year hydrostatic test window and has adequate remaining weight capacity to avoid overfilling.",
            citation = "DOT 49 CFR § 173.304",
            isA2L = false
        ),
        Question(
            id = "t1-35",
            section = EPASection.TYPE_I,
            questionText = "What is the primary operational symptom of non-condensable gas in a refrigeration system?",
            options = listOf("Abnormally low suction pressure", "Abnormally high discharge (head) pressure and reduced cooling capacity", "Frost formation on the suction line", "The compressor runs backward"),
            correctIndex = 1,
            explanation = "Air trapped in the condenser takes up surface area and cannot be condensed at normal pressures, increasing head pressure and compressor work.",
            citation = "HVAC Troubleshooting Handbook",
            isA2L = false
        ),
        Question(
            id = "t1-36",
            section = EPASection.TYPE_I,
            questionText = "Under EPA regulations, what must be done with small appliances containing CFC or HCFC refrigerants when discarded by a homeowner on curbside pickup?",
            options = listOf("The trash collector may vent the refrigerant if the appliance is outside", "The municipality or scrap recycler must ensure refrigerant is recovered before crushing or shredding", "Homeowners are legally required to vent the gas before putting it on the curb", "The appliance must be incinerated in a municipal waste furnace"),
            correctIndex = 1,
            explanation = "EPA rules strictly prohibit shredding or crushing appliances before recovering refrigerant. Municipalities and scrap yards must document recovery.",
            citation = "40 CFR § 82.156(f)",
            isA2L = false
        ),
        Question(
            id = "t1-37",
            section = EPASection.TYPE_I,
            questionText = "Which refrigerant is commonly used in modern domestic wine coolers and compact refrigerators due to its low GWP?",
            options = listOf("R-22", "R-600a (Isobutane)", "CFC-11", "R-502"),
            correctIndex = 1,
            explanation = "Isobutane (R-600a) is an A3 hydrocarbon refrigerant with zero ODP and a GWP of 3, widely adopted in domestic refrigeration under EPA SNAP approvals.",
            citation = "EPA SNAP Rule 20/21",
            isA2L = true
        ),
        Question(
            id = "t1-38",
            section = EPASection.TYPE_I,
            questionText = "What is the charge limit for propane (R-290) in new commercial stand-alone reach-in food refrigerators under updated UL and EPA SNAP standards?",
            options = listOf("150 grams", "300 to 500 grams", "5 pounds", "10 pounds"),
            correctIndex = 1,
            explanation = "Commercial stand-alone food service units with open or closed display cases can contain up to 300 to 500 grams of R-290 under modern UL 60335-2-89 standards.",
            citation = "UL 60335-2-89 / EPA SNAP",
            isA2L = true
        ),
        Question(
            id = "t1-39",
            section = EPASection.TYPE_I,
            questionText = "When using an active recovery machine, why must the recovery unit's condenser coil be kept cool?",
            options = listOf("To prevent the recovery compressor motor from tripping on thermal overload and maintain high condensing rate", "To keep the refrigerant at -40°F", "To freeze any moisture in the recovery hoses", "To prevent the cylinder paint from peeling"),
            correctIndex = 0,
            explanation = "Recovery machine compressors generate substantial heat. Keeping the recovery condenser cool lowers discharge pressure and maximizes vapor condensation speed.",
            citation = "AHRI Standard 740",
            isA2L = false
        ),
        Question(
            id = "t1-40",
            section = EPASection.TYPE_I,
            questionText = "What should a technician do if they suspect that the refrigerant in a small appliance is contaminated with an unknown chemical substance?",
            options = listOf("Vent the gas into the customer's crawlspace", "Recover the gas into a dedicated, separate recovery cylinder clearly labeled as contaminated waste, and do not mix it with clean recovered stocks", "Burn the refrigerant in an open campfire", "Pour water into the system to flush the chemical"),
            correctIndex = 1,
            explanation = "Contaminated gas must be isolated to avoid contaminating certified cylinders. Reclaimers require specialized analysis and hazardous waste handling.",
            citation = "AHRI Guideline K",
            isA2L = false
        ),
        Question(
            id = "t1-41",
            section = EPASection.TYPE_I,
            questionText = "What device can be used to capture refrigerant from a small appliance if no electrical power is available on the job site?",
            options = listOf("A motorized recovery compressor powered by a car battery", "A passive recovery system utilizing a specialized evacuated cylinder or non-pressurized bag with ice", "An air compressor run in reverse", "Venting the refrigerant directly into a trash can"),
            correctIndex = 1,
            explanation = "Passive recovery requires no external electric motor, using the natural vapor pressure differential created by chilling an evacuated cylinder.",
            citation = "40 CFR § 82.156(a)",
            isA2L = false
        ),
        Question(
            id = "t1-42",
            section = EPASection.TYPE_I,
            questionText = "How can the oil in a small appliance compressor be prevented from foaming excessively during evacuation?",
            options = listOf("By heating the crankcase slowly and evacuating gradually rather than pulling sudden deep vacuum surges", "By pouring dish soap into the service port", "By filling the suction line with cold water", "By reversing the rotation of the motor"),
            correctIndex = 0,
            explanation = "Rapid pressure drops cause liquid refrigerant dissolved in oil to flash violently, creating foam. Gradual evacuation keeps oil in the crankcase.",
            citation = "Tecumseh Technical Guidelines",
            isA2L = false
        ),
        Question(
            id = "t1-43",
            section = EPASection.TYPE_I,
            questionText = "What safety classification does refrigerant R-600a hold under ASHRAE Standard 34?",
            options = listOf("A1", "A2L", "A3", "B1"),
            correctIndex = 2,
            explanation = "Isobutane (R-600a) is classified as Class A3: non-toxic (Class A) but highly flammable (Class 3).",
            citation = "ASHRAE Standard 34",
            isA2L = true
        ),
        Question(
            id = "t1-44",
            section = EPASection.TYPE_I,
            questionText = "What safety classification does refrigerant R-134a hold under ASHRAE Standard 34?",
            options = listOf("A1", "A2L", "A3", "B2"),
            correctIndex = 0,
            explanation = "R-134a (tetrafluoroethane) is classified as Class A1: non-toxic and non-flammable under standard atmospheric testing conditions.",
            citation = "ASHRAE Standard 34",
            isA2L = false
        ),
        Question(
            id = "t1-45",
            section = EPASection.TYPE_I,
            questionText = "What is the typical atmospheric boiling point of refrigerant R-134a at sea level pressure (14.7 psia)?",
            options = listOf("-15.3°F (-26.3°C)", "32.0°F (0.0°C)", "212.0°F (100.0°C)", "-50.0°F (-45.5°C)"),
            correctIndex = 0,
            explanation = "R-134a boils at approximately -15.3°F at standard atmospheric pressure, making it an effective medium-temperature refrigerant.",
            citation = "Refrigerant Thermodynamic Tables",
            isA2L = false
        ),
        Question(
            id = "t1-46",
            section = EPASection.TYPE_I,
            questionText = "What is the typical atmospheric boiling point of refrigerant R-600a (isobutane) at sea level?",
            options = listOf("10.9°F (-11.7°C)", "-40.0°F (-40.0°C)", "75.0°F (23.9°C)", "150.0°F (65.5°C)"),
            correctIndex = 0,
            explanation = "Isobutane (R-600a) boils at approximately 10.9°F (-11.7°C) at atmospheric pressure, operating at lower pressures than R-134a.",
            citation = "ASHRAE Fundamentals",
            isA2L = true
        ),
        Question(
            id = "t1-47",
            section = EPASection.TYPE_I,
            questionText = "When servicing a small appliance hermetic system with an open flame torch, what distance must be maintained from any flammable hydrocarbon materials?",
            options = listOf("At least 1 foot", "At least 10 feet with adequate room ventilation and a dry chemical fire extinguisher present", "6 inches", "No distance is specified"),
            correctIndex = 1,
            explanation = "Safety standards mandate maintaining at least 10 feet of clearance from flammable vapors, continuous room ventilation, and an accessible Class B fire extinguisher.",
            citation = "NFPA 54 / OSHA Safety Rules",
            isA2L = true
        ),
        Question(
            id = "t1-48",
            section = EPASection.TYPE_I,
            questionText = "What is the primary indicator of a moisture restriction in a small appliance capillary tube circuit?",
            options = listOf("The appliance cools perfectly for 15-30 minutes, then stops cooling completely as ice freezes in the capillary tube outlet, and resumes cooling after thawing", "The condenser fan motor catches fire", "The compressor starts vibrating loudly immediately upon startup", "The refrigerator light stays on continuously"),
            correctIndex = 0,
            explanation = "Free moisture circulates until it hits the below-freezing expansion point at the capillary outlet, where it forms an ice plug. Once the unit warms, the ice melts and cooling resumes intermittently.",
            citation = "RSES Refrigeration Service Guide",
            isA2L = false
        ),
        Question(
            id = "t1-49",
            section = EPASection.TYPE_I,
            questionText = "How can a technician remove moisture from a small appliance refrigeration circuit before recharging?",
            options = listOf("By blowing oxygen through the tubes", "By replacing the liquid line filter-drier and pulling a deep vacuum below 500 microns with a two-stage vacuum pump", "By adding liquid water to dissolve the acid", "By heating the evaporator with an open blowtorch"),
            correctIndex = 1,
            explanation = "Installing a fresh high-capacity filter-drier and evacuating to 500 microns vaporizes liquid water and removes it through the vacuum pump exhaust.",
            citation = "EPA 608 Dehydration Best Practices",
            isA2L = false
        ),
        Question(
            id = "t1-50",
            section = EPASection.TYPE_I,
            questionText = "Why should technicians NEVER store flammable refrigerants (R-600a or R-290) in an unventilated vehicle trunk in direct sunlight?",
            options = listOf("The cans will rust", "Solar heating can cause internal container pressure to exceed burst thresholds, resulting in explosive rupture and fire inside the vehicle", "The refrigerant will lose its chemical odor", "The gas will solidify into ice"),
            correctIndex = 1,
            explanation = "Direct sun exposure can heat vehicle interiors above 140°F, creating severe overpressurization risks for hydrocarbon aerosol cans and cylinders.",
            citation = "DOT Hazardous Materials Handling",
            isA2L = true
        ),
        Question(
            id = "t1-51",
            section = EPASection.TYPE_I,
            questionText = "What severe risk is presented when servicing a hermetic compressor with electrical terminal pins that show signs of carbonization or oil leakage?",
            options = listOf("The motor will run backwards", "A terminal blowout can occur under pressure, ejecting the terminal pin and spraying atomized oil and toxic acid gas at high velocity", "The compressor will generate 480 volts internally", "The copper tubing will turn brittle"),
            correctIndex = 1,
            explanation = "Arcing degrades the glass-to-metal terminal seal. Pressurizing or running the compressor can blow out the pin, creating an explosive fireball or caustic spray.",
            citation = "ASHRAE Safety Alert Bulletin",
            isA2L = false
        ),
        Question(
            id = "t1-52",
            section = EPASection.TYPE_I,
            questionText = "Why must dry nitrogen be swept through copper lines at 2 to 5 psig while brazing a replacement compressor on a small appliance?",
            options = listOf("To keep the copper pipe cold to the touch", "To displace atmospheric oxygen and prevent the formation of black cupric oxide scale inside the tubing that plugs capillary tubes", "To speed up the melting of the brazing rod", "To prevent the solder from adhering to the brass fittings"),
            correctIndex = 1,
            explanation = "Heating copper without nitrogen produces internal copper oxide flaking. These tiny abrasive flakes wash into the capillary tube, causing permanent restriction.",
            citation = "ACCA Quality Installation Standards",
            isA2L = false
        ),
        Question(
            id = "t1-53",
            section = EPASection.TYPE_I,
            questionText = "When retrofitting an older R-12 domestic freezer to R-134a, what lubricant modification is mandatory?",
            options = listOf("The mineral oil must be completely drained and replaced with synthetic polyolester (POE) oil", "Vegetable cooking oil should be added to mineral oil", "No oil change is necessary because R-134a mixes with mineral oil", "The oil must be mixed with water to prevent foaming"),
            correctIndex = 0,
            explanation = "R-134a is insoluble in mineral oil and will not return to the compressor. POE synthetic lubricant must be installed with less than 1% residual mineral oil.",
            citation = "DuPont Suva Retrofit Guidelines",
            isA2L = false
        ),
        Question(
            id = "t1-54",
            section = EPASection.TYPE_I,
            questionText = "When recharging an R-134a retrofitted domestic refrigerator originally designed for R-12, what is the typical final charge quantity relative to the original nameplate?",
            options = listOf("150% of original charge", "85% to 90% of the original R-12 nameplate weight", "Exactly 50% of original charge", "Double the original weight"),
            correctIndex = 1,
            explanation = "R-134a has a lower liquid density than R-12. Charging approximately 85% to 90% by weight matches original evaporator flood levels and prevents overcharging.",
            citation = "AHAM Retrofit Guidelines",
            isA2L = false
        ),
        Question(
            id = "t1-55",
            section = EPASection.TYPE_I,
            questionText = "What precision tool should be used to weigh fractional-ounce refrigerant charges into small appliances?",
            options = listOf("A bathroom mechanical spring scale", "A digital electronic charging scale with 0.1-ounce (1-gram) resolution", "A volumetric sight glass only", "Estimating by counting compressor strokes"),
            correctIndex = 1,
            explanation = "Small appliances contain critical charges often under 4 to 8 ounces. Overcharging by as little as 0.5 oz causes high head pressure and frosted suction lines.",
            citation = "RSES Service Practices",
            isA2L = false
        ),
        Question(
            id = "t1-56",
            section = EPASection.TYPE_I,
            questionText = "What is the maximum acceptable moisture level inside an operating hermetic small appliance refrigeration system?",
            options = listOf("Under 20 parts per million (ppm)", "1,000 ppm", "10% water by volume", "500 parts per million"),
            correctIndex = 0,
            explanation = "Hermetic systems must maintain moisture levels below 20 ppm; higher levels cause copper plating, acid formation, and capillary tube ice blockage.",
            citation = "AHRI Standard 710",
            isA2L = false
        ),
        Question(
            id = "t1-57",
            section = EPASection.TYPE_I,
            questionText = "What is the purpose of the oil separator incorporated into high-performance small appliance recovery units?",
            options = listOf("To add motor oil to the appliance", "To separate entrained compressor oil from recovered refrigerant and return it or drain it for measurement", "To cool the recovery compressor motor", "To convert oil into vapor"),
            correctIndex = 1,
            explanation = "Extracting oil ensures clean refrigerant enters the recovery cylinder and allows the technician to measure oil lost and add fresh oil back to the repaired compressor.",
            citation = "AHRI Standard 740",
            isA2L = false
        ),
        Question(
            id = "t1-58",
            section = EPASection.TYPE_I,
            questionText = "What tool is used to crimp and permanently seal off a copper process tube before brazing on a small appliance hermetic circuit?",
            options = listOf("Slip-joint pliers", "A specialized mechanical pinch-off tool", "Vise grips with serrated teeth", "A pipe cutter"),
            correctIndex = 1,
            explanation = "A calibrated pinch-off tool crushes the copper tube into a solid, cold-welded flat seam, preventing vapor leakage while the end is brazed shut.",
            citation = "Tecumseh Hermetic Service Manual",
            isA2L = false
        ),
        Question(
            id = "t1-59",
            section = EPASection.TYPE_I,
            questionText = "What is the primary operational symptom of an overcharged small appliance refrigeration system with a capillary tube metering device?",
            options = listOf("Elevated discharge pressure, higher current draw, reduced subcooling, and frost tracking past the evaporator onto the suction line", "Low head pressure and warm evaporator", "The compressor thermal overload trips after 48 hours of complete silence", "Rapid short cycling every 2 seconds"),
            correctIndex = 0,
            explanation = "Excess refrigerant backs up into the condenser, reducing condensing area, and over-floods the evaporator, pushing liquid down the suction line into the compressor.",
            citation = "RSES Troubleshooting Guide",
            isA2L = false
        ),
        Question(
            id = "t1-60",
            section = EPASection.TYPE_I,
            questionText = "What is the primary operational symptom of an undercharged small appliance refrigeration system?",
            options = listOf("Low suction pressure, low head pressure, high superheat, and partial or patchy frost on only the first few turns of the evaporator coil", "Frost completely encasing the compressor shell", "Head pressure exceeding 400 psig", "The motor starts running at 7,200 RPM"),
            correctIndex = 0,
            explanation = "Starving the evaporator leaves insufficient liquid to boil across the coil, resulting in low evaporating pressure, poor cooling, and high vapor superheat.",
            citation = "AHAM Technical Bulletin",
            isA2L = false
        ),
        Question(
            id = "t1-61",
            section = EPASection.TYPE_I,
            questionText = "Why does a domestic refrigerator compressor run continuously when the condenser coil is heavily caked with household dust and pet hair?",
            options = listOf("The dust absorbs all electrical current", "Reduced airflow impairs heat rejection, elevating condensing temperature and pressure, which slashes compressor pumping capacity and efficiency", "The dust short-circuits the thermostat wire", "The fan motor reverses direction automatically"),
            correctIndex = 1,
            explanation = "Inadequate condenser heat transfer raises head pressure, increasing the compression ratio. The system cannot lower evaporator temperature to the thermostat cutout point.",
            citation = "Appliance Efficiency Handbook",
            isA2L = false
        ),
        Question(
            id = "t1-62",
            section = EPASection.TYPE_I,
            questionText = "When performing a system-dependent recovery on a domestic freezer with an operating compressor, what device can be used to hold open the low-side check valve if present?",
            options = listOf("No device is needed; the operational compressor will pump refrigerant from the low side through the discharge process port", "A neodymium magnet placed on the outside of the copper tube", "A 24-volt battery jumper", "A high-pressure water hose"),
            correctIndex = 0,
            explanation = "Running the appliance compressor forces refrigerant through the high side into the recovery cylinder without needing special low-side bypassing.",
            citation = "40 CFR § 82.156(a)",
            isA2L = false
        ),
        Question(
            id = "t1-63",
            section = EPASection.TYPE_I,
            questionText = "Why should hoses used to service R-600a or R-290 systems be as short as possible?",
            options = listOf("To prevent hose tangles on the floor", "To minimize the volume of trapped hydrocarbon vapor released upon disconnection and avoid inaccurate charge amounts on micro-charge systems", "Because long hoses cause flammable gas to ignite spontaneously", "Because hydrocarbon hoses must be made of solid steel"),
            correctIndex = 1,
            explanation = "In systems charged with only 1.5 to 3 ounces of hydrocarbon, standard charging hoses can hold a significant fraction of the total charge, distorting operating levels.",
            citation = "EPA SNAP Service Guide",
            isA2L = true
        ),
        Question(
            id = "t1-64",
            section = EPASection.TYPE_I,
            questionText = "What type of electrical switches must be used in service equipment (vacuum pumps, recovery machines, leak detectors) when handling hydrocarbon refrigerants?",
            options = listOf("Standard residential light switches", "Spark-proof, sealed, intrinsically safe switches meeting ATEX or Class I, Division 2 standards", "Knife-blade open copper switches", "Mercury switches"),
            correctIndex = 1,
            explanation = "Intrinsically safe equipment isolates electrical arcing points to prevent igniting combustible air-fuel mixtures in the event of a refrigerant release.",
            citation = "UL 121201 / NFPA 70",
            isA2L = true
        ),
        Question(
            id = "t1-65",
            section = EPASection.TYPE_I,
            questionText = "What should a technician do immediately if a process tube fitting begins leaking flammable R-600a during servicing in a residential kitchen?",
            options = listOf("Turn on the kitchen gas stove to burn it off", "Extinguish all open flames, de-energize electrical devices from a remote breaker, open all windows and exterior doors for ventilation, and evacuate the immediate area", "Cover the leak with a wet towel and sit on it", "Turn on an electric vacuum cleaner to suck up the vapor"),
            correctIndex = 1,
            explanation = "Never operate electrical switches or appliances near flammable gas leaks. Maximum mechanical and natural ventilation must be established without spark sources.",
            citation = "NFPA 54 / OSHA Safety Directives",
            isA2L = true
        ),
        Question(
            id = "t1-66",
            section = EPASection.TYPE_I,
            questionText = "What is the typical evaporator temperature in a properly operating domestic zero-degree food storage freezer?",
            options = listOf("32°F (0°C)", "10°F to 20°F", "-10°F to -15°F (-23°C to -26°C)", "45°F (7.2°C)"),
            correctIndex = 2,
            explanation = "To maintain 0°F air temperature, the evaporator coil must boil refrigerant at a temperature differential (TD) of approximately 10°F to 15°F colder, or -10°F to -15°F.",
            citation = "ASHRAE Refrigeration Systems",
            isA2L = false
        ),
        Question(
            id = "t1-67",
            section = EPASection.TYPE_I,
            questionText = "What is the typical saturation pressure of R-134a at -10°F in a domestic freezer evaporator?",
            options = listOf("35 psig", "18 psig", "Approximately 1.9 to 2.0 psig (or slight vacuum at high altitudes)", "100 psig"),
            correctIndex = 2,
            explanation = "Per the R-134a P-T chart, at -10°F the saturation pressure is just under 2 psig at sea level, meaning freezer low sides operate very close to atmospheric pressure.",
            citation = "R-134a Saturation Table",
            isA2L = false
        ),
        Question(
            id = "t1-68",
            section = EPASection.TYPE_I,
            questionText = "Why are R-600a systems designed to operate with low-side pressures in a partial vacuum during normal freezing operation?",
            options = listOf("To prevent the compressor from overheating", "Because R-600a has a boiling point of 10.9°F; achieving -10°F evaporator temperatures requires evaporating pressures below atmospheric pressure (~5 inches Hg vacuum)", "Because vacuum makes the refrigerant non-flammable", "Because the factory wanted to eliminate all air"),
            correctIndex = 1,
            explanation = "Thermodynamic saturation curves dictate that boiling R-600a below its 10.9°F atmospheric boiling point requires sub-atmospheric (vacuum) suction pressure.",
            citation = "R-600a Pressure-Temperature Properties",
            isA2L = true
        ),
        Question(
            id = "t1-69",
            section = EPASection.TYPE_I,
            questionText = "What risk arises if a low-side leak occurs in an operating R-600a domestic freezer whose suction runs in a partial vacuum?",
            options = listOf("Refrigerant will spray across the room at 200 psig", "Atmospheric air and moisture will be sucked into the system through the leak, raising head pressure and creating a potential combustible air-hydrocarbon mixture in the compressor", "The evaporator coil will immediately turn into ice cubes", "The electrical cord will melt"),
            correctIndex = 1,
            explanation = "A leak in a vacuum sucks ambient air in. Air introduces oxygen and moisture into the sealed crankcase, where compression heat could potentially ignite hydrocarbons.",
            citation = "UL 60335-2-24 Safety Hazard Analysis",
            isA2L = true
        ),
        Question(
            id = "t1-70",
            section = EPASection.TYPE_I,
            questionText = "What is the primary function of the anti-sweat loop (mullion heater loop) found in modern domestic refrigerators?",
            options = listOf("To defrost the freezer evaporator coil every 6 hours", "To route hot high-pressure discharge gas through the metal cabinet door flange to prevent ambient humidity from condensing on the exterior casing", "To cool the compressor oil", "To heat food stored on the top shelf"),
            correctIndex = 1,
            explanation = "Hot discharge tubing routed around door perimeters warms the cabinet face above the room dew point, preventing external sweat and cabinet corrosion.",
            citation = "Appliance Engineering Principles",
            isA2L = false
        ),
        Question(
            id = "t1-71",
            section = EPASection.TYPE_I,
            questionText = "What does an insulation resistance reading of less than 0.5 megohms (500,000 ohms) to ground indicate on a hermetic compressor motor stator?",
            options = listOf("Normal winding resistance for an inverter compressor", "Motor electrical insulation breakdown and severe moisture/acid contamination, indicating a motor nearing total electrical failure or burnout", "The compressor is in brand-new condition", "The system is completely dry"),
            correctIndex = 1,
            explanation = "Compressor motor insulation should read at least 20 to 100+ megohms. A reading below 0.5 megohms indicates advanced insulation breakdown and imminent burnout.",
            citation = "EASA Motor Testing Guidelines",
            isA2L = false
        ),
        Question(
            id = "t1-72",
            section = EPASection.TYPE_I,
            questionText = "Why must technicians NEVER use a standard incandescent test light or open-spark device inside an open domestic refrigerator charged with R-600a?",
            options = listOf("The light bulb heat melts the plastic shelves", "Breaking or arcing bulb filaments can ignite any accumulated R-600a vapor pooled in the bottom of the cabinet", "The light bulb consumes too much power", "The light ruins the taste of milk"),
            correctIndex = 1,
            explanation = "R-600a is heavier than air and pools in cabinet sumps. Any electrical spark or exposed hot filament can ignite vapor within its Lower Flammability Limit (LFL 1.8%).",
            citation = "UL 60335-2-24 Hazardous Location Rules",
            isA2L = true
        ),
        Question(
            id = "t1-73",
            section = EPASection.TYPE_I,
            questionText = "What is the Lower Flammability Limit (LFL) of R-600a (isobutane) in air by volume percentage?",
            options = listOf("0.1%", "1.8% (approx. 43 g/m³)", "15.0%", "50.0%"),
            correctIndex = 1,
            explanation = "R-600a has an LFL of approximately 1.8% by volume. Concentrations below 1.8% are too lean to ignite.",
            citation = "ASHRAE Standard 34",
            isA2L = true
        ),
        Question(
            id = "t1-74",
            section = EPASection.TYPE_I,
            questionText = "What is the Upper Flammability Limit (UFL) of R-600a in air by volume percentage?",
            options = listOf("5.0%", "8.5% (approx. 205 g/m³)", "25.0%", "100.0%"),
            correctIndex = 1,
            explanation = "The flammability range of isobutane in air is 1.8% to 8.5% by volume. Above 8.5%, the mixture is too rich to support combustion.",
            citation = "ASHRAE Standard 34",
            isA2L = true
        ),
        Question(
            id = "t1-75",
            section = EPASection.TYPE_I,
            questionText = "Under what condition is an EPA Section 608 Universal technician permitted to service Type I Small Appliances?",
            options = listOf("Only under direct supervision of a Master Plumber", "At all times; a Universal certification legally covers Type I, Type II, and Type III equipment disciplines", "Only if the technician pays an annual state surcharge", "Universal technicians are prohibited from touching small appliances"),
            correctIndex = 1,
            explanation = "Universal certification is awarded to candidates who pass Core and all three individual disciplines (Type I, II, and III), authorizing work across all categories.",
            citation = "40 CFR § 82.161",
            isA2L = false
        ),
        Question(
            id = "t1-76",
            section = EPASection.TYPE_I,
            questionText = "What is the typical result of connecting an active recovery machine to an appliance using 1/4\" charging hoses with worn, crushed Schrader depressor gaskets?",
            options = listOf("The machine recovers vapor 50% faster", "The crushed gasket restricts refrigerant flow or blocks access completely, causing excessive recovery times and inaccurate vacuum gauge readings", "The recovery machine compressor reverses rotation", "The cylinder explodes from excess vacuum"),
            correctIndex = 1,
            explanation = "Damaged hose core depressors obstruct the valve orifice, creating massive pressure drops that choke refrigerant transfer.",
            citation = "Fieldpiece Technical Bulletin",
            isA2L = false
        ),
        Question(
            id = "t1-77",
            section = EPASection.TYPE_I,
            questionText = "How should recovered refrigerant from small appliances be labeled on the cylinder tag under DOT regulations?",
            options = listOf("Labeled as 'Clean Domestic Gas'", "Marked with the DOT proper shipping name, UN identification number, hazard class, and chemical refrigerant type (e.g., 'UN 3159, 1,1,1,2-Tetrafluoroethane, Class 2.2')", "Marked only with the technician's initials in pencil", "No labeling is required for quantities under 100 pounds"),
            correctIndex = 1,
            explanation = "DOT mandates specific shipping names, 4-digit UN numbers, and diamond hazard placards for compressed gases transported on public roadways.",
            citation = "49 CFR § 172.301",
            isA2L = false
        ),
        Question(
            id = "t1-78",
            section = EPASection.TYPE_I,
            questionText = "What is the DOT classification and UN number for R-134a?",
            options = listOf("Division 2.1 Flammable Gas, UN 1978", "Division 2.2 Non-Flammable Gas, UN 3159", "Class 3 Flammable Liquid, UN 1203", "Class 8 Corrosive Gas, UN 1760"),
            correctIndex = 1,
            explanation = "R-134a is non-flammable compressed gas classified under DOT Division 2.2 and assigned UN identification number UN 3159.",
            citation = "DOT Hazardous Materials Table 49 CFR § 172.101",
            isA2L = false
        ),
        Question(
            id = "t1-79",
            section = EPASection.TYPE_I,
            questionText = "What is the DOT classification and UN number for R-600a (isobutane)?",
            options = listOf("Division 2.1 Flammable Gas, UN 1969", "Division 2.2 Non-Flammable Gas, UN 1078", "Division 2.3 Poison Gas, UN 1005", "Class 9 Miscellaneous, UN 3077"),
            correctIndex = 0,
            explanation = "Isobutane is a flammable gas classified under DOT Division 2.1 and assigned UN identification number UN 1969.",
            citation = "DOT Hazardous Materials Table 49 CFR § 172.101",
            isA2L = true
        ),
        Question(
            id = "t1-80",
            section = EPASection.TYPE_I,
            questionText = "What is the DOT classification and UN number for R-290 (propane)?",
            options = listOf("Division 2.1 Flammable Gas, UN 1978", "Division 2.2 Non-Flammable Gas, UN 3159", "Class 1 Explosive, UN 0042", "Class 6 Toxic Substance, UN 2811"),
            correctIndex = 0,
            explanation = "Propane refrigerant is classified under DOT Division 2.1 Flammable Gas and assigned UN identification number UN 1978.",
            citation = "DOT Hazardous Materials Table 49 CFR § 172.101",
            isA2L = true
        ),
        Question(
            id = "t1-81",
            section = EPASection.TYPE_I,
            questionText = "What should be done with disposable 1-pound or small threaded cans of R-134a or R-600a after they have been used to charge an appliance?",
            options = listOf("Throw them into an open fire to burn the propellant", "Ensure they are fully depleted to atmospheric pressure (0 psig) and recycle them as clean scrap metal", "Refill them with nitrogen for shop blowguns", "Puncture them while still pressurized to vent residual liquid"),
            correctIndex = 1,
            explanation = "Empty non-refillable consumer and service cans must be evacuated to 0 psig before recycling or scrap disposal per EPA and OSHA rules.",
            citation = "EPA 40 CFR § 82.156(f)",
            isA2L = false
        ),
        Question(
            id = "t1-82",
            section = EPASection.TYPE_I,
            questionText = "What occurs when liquid refrigerant is charged into the suction service port of an operating small hermetic compressor without using a throttling restrictor?",
            options = listOf("The compressor runs cooler and draws less current", "Liquid slugging occurs, causing severe mechanical damage to reciprocating intake reeds, piston heads, and connecting rods", "The oil is automatically clarified", "The head pressure drops to zero"),
            correctIndex = 1,
            explanation = "Liquids are non-compressible. Drawing liquid directly into compressor cylinders causes hydraulic lock, bending connecting rods and snapping valve reeds.",
            citation = "Copeland Compressor Application Bulletin",
            isA2L = false
        ),
        Question(
            id = "t1-83",
            section = EPASection.TYPE_I,
            questionText = "When checking the electrical resistance across the three terminals (Common, Start, Run) of a single-phase small compressor motor, what mathematical relationship must hold true?",
            options = listOf("Resistance between Common and Start plus Common and Run equals the total resistance measured between Start and Run", "All three terminal pairs must read exactly zero ohms", "Start-to-Run resistance must be less than Common-to-Run resistance", "Common-to-Ground must read 10 ohms"),
            correctIndex = 0,
            explanation = "The Start and Run windings are wired in series through the Common terminal; therefore, R(Start-to-Run) = R(Common-to-Start) + R(Common-to-Run).",
            citation = "HVAC Electrical Troubleshooting Manual",
            isA2L = false
        ),
        Question(
            id = "t1-84",
            section = EPASection.TYPE_I,
            questionText = "What does an infinite (open-circuit) resistance reading across the Common terminal and the internal motor windings indicate on a hot compressor?",
            options = listOf("The motor is completely burned out and shorted to ground", "The internal thermal overload protector (Klixon) has tripped open due to excessive heat or current draw, and is waiting to cool down", "The compressor has lost its refrigerant charge", "The start capacitor is fully charged"),
            correctIndex = 1,
            explanation = "Internal thermal protectors are wired in series with the Common pin and snap open at ~200°F. Once cooled (which can take hours), they snap shut.",
            citation = "Tecumseh Hermetic Troubleshooting Guide",
            isA2L = false
        ),
        Question(
            id = "t1-85",
            section = EPASection.TYPE_I,
            questionText = "What is the primary function of a Positive Temperature Coefficient (PTC) solid-state start relay on a domestic refrigerator compressor?",
            options = listOf("To regulate the temperature of the vegetable crisper", "To provide high starting current to the start winding, and then rapidly increase in electrical resistance as it heats up, effectively dropping the start winding out of the circuit", "To protect against lightning strikes", "To reverse motor rotation during defrost cycles"),
            correctIndex = 1,
            explanation = "PTC ceramic thermistors pass high current to start the motor, then heat within fractions of a second to high resistance (>10,000 ohms), shutting off the start winding.",
            citation = "Embraco Service Manual",
            isA2L = false
        ),
        Question(
            id = "t1-86",
            section = EPASection.TYPE_I,
            questionText = "Why must technicians be cautious when using clamp-on amp meters to measure running current on high-efficiency ECM (inverter-driven) small compressors?",
            options = listOf("Because ECM compressors operate on direct current (DC) with variable-frequency pulse-width-modulated (PWM) wave signals that standard AC meters cannot read accurately", "Because ECM compressors draw over 500 amps", "Because the magnetic field erases the technician's credit cards", "Because ECM compressors have no electrical wires"),
            correctIndex = 0,
            explanation = "Inverter compressors receive 3-phase synthesized DC/AC PWM power from an inverter board. Standard 60Hz clamp meters yield wildly inaccurate readings; True-RMS meters are required.",
            citation = "Inverter Compressor Service Handbook",
            isA2L = false
        ),
        Question(
            id = "t1-87",
            section = EPASection.TYPE_I,
            questionText = "What is the proper method for leak checking the evaporator coil inside a domestic chest freezer where electronic detectors cannot pinpoint the leak?",
            options = listOf("Submerge the entire freezer under water while plugged into 120V", "Isolate the evaporator from the compressor, pressurize with dry nitrogen to nameplate test pressure (max 150 psig), and perform a standing pressure decay test with soap bubble solution", "Fill the evaporator with gasoline to see where it drips", "Heat the evaporator with a weed burner"),
            correctIndex = 1,
            explanation = "Isolating sections and pressure testing with dry nitrogen prevents overpressurizing weak aluminum evaporator joints while pinpointing leaks.",
            citation = "EPA Type I Field Guidelines",
            isA2L = false
        ),
        Question(
            id = "t1-88",
            section = EPASection.TYPE_I,
            questionText = "Under 40 CFR § 82.156(a), what is the recovery requirement when using a system-dependent recovery unit on a small appliance with a NON-OPERATING compressor?",
            options = listOf("Must recover 50% of the nameplate charge", "Must recover at least 80% of the nameplate charge (or achieve a 4-inch Hg vacuum) accessing both the high and low process stubs", "No recovery is required; venting is permitted", "Must pull a 29-inch vacuum in 5 seconds"),
            correctIndex = 1,
            explanation = "80% recovery is the statutory standard for inoperative compressors, achievable only by tapping both sides to bypass the capillary tube restriction.",
            citation = "40 CFR § 82.156(a)(1)",
            isA2L = false
        ),
        Question(
            id = "t1-89",
            section = EPASection.TYPE_I,
            questionText = "Why should service technicians avoid using leak-stop chemical sealants in small appliance refrigeration circuits?",
            options = listOf("Because sealants chemically react with residual moisture, creating solid polymers that permanently plug capillary tubes, expansion valves, and recovery machine orifices", "Because sealants turn the refrigerant into chlorine gas", "Because sealants are radioactive", "Because sealants make the refrigerator too cold"),
            correctIndex = 0,
            explanation = "Liquid sealants cure when exposed to moisture or air, forming hardened polymer plugs that choke tiny capillary tube bores and ruin recovery equipment.",
            citation = "AHRI Chemical Bulletin",
            isA2L = false
        ),
        Question(
            id = "t1-90",
            section = EPASection.TYPE_I,
            questionText = "What is the typical diameter of a copper capillary tube metering device used in domestic refrigerators?",
            options = listOf("1/2 inch to 3/4 inch inside diameter", "0.026 inch to 0.042 inch inside diameter (less than 1 millimeter)", "1.5 inches", "Exactly 1 inch"),
            correctIndex = 1,
            explanation = "Capillary tubes have extremely small inner diameters (typically 0.031\" to 0.042\"). Even microscopic debris, moisture ice, or oil sludge will cause complete blockage.",
            citation = "ASHRAE Refrigeration Handbook",
            isA2L = false
        ),
        Question(
            id = "t1-91",
            section = EPASection.TYPE_I,
            questionText = "How can a technician verify that a capillary tube restriction has been successfully cleared after flushing with nitrogen?",
            options = listOf("By listening for an open rushing sound and observing equal pressure transmission between high and low sides", "By smelling the copper tube", "By checking if the copper tube changes color to green", "By running the fan motor on high speed"),
            correctIndex = 0,
            explanation = "Free passage of dry nitrogen with immediate pressure equalization across the restriction confirms the capillary bore is clear.",
            citation = "RSES Technical Bulletin",
            isA2L = false
        ),
        Question(
            id = "t1-92",
            section = EPASection.TYPE_I,
            questionText = "When installing a replacement hermetic compressor on an appliance, what should be done with the rubber shipping plugs in the compressor stubs?",
            options = listOf("Leave them inside the tubes and braze right through them", "Remove them only immediately before brazing to prevent atmospheric moisture and air from entering the dry POE lubricant charge", "Discard them into the crankcase", "Melt them with a torch beforehand"),
            correctIndex = 1,
            explanation = "New compressors are charged with dry nitrogen and synthetic POE oil. Unplugging them prematurely exposes hygroscopic POE oil to atmospheric humidity.",
            citation = "Tecumseh Installation Instructions",
            isA2L = false
        ),
        Question(
            id = "t1-93",
            section = EPASection.TYPE_I,
            questionText = "What happens if a technician brazes a process tube with high-silver alloy without using appropriate brazing flux on brass-to-copper joints?",
            options = listOf("The joint will look shiny", "Oxides will prevent the filler metal from wetting and adhering to the brass, causing pinhole leaks under vibration", "The brass fitting will turn into solid copper", "The joint will absorb nitrogen from the air"),
            correctIndex = 1,
            explanation = "While copper-to-copper joints can be brazed with BCuP phosphorus alloys without flux, any joint involving brass, steel, or silver requires flux to dissolve oxides.",
            citation = "AWS Brazing Handbook",
            isA2L = false
        ),
        Question(
            id = "t1-94",
            section = EPASection.TYPE_I,
            questionText = "What safety gear must be kept nearby whenever recovering flammable refrigerants (R-600a, R-290)?",
            options = listOf("A container of gasoline", "A properly rated, fully charged dry chemical (Class B/C) fire extinguisher and a combustible gas leak detector", "An open kerosene lantern", "A bucket of sand only"),
            correctIndex = 1,
            explanation = "Standard safety procedures require a dry chemical Class B/C fire extinguisher on site during any service involving flammable hydrocarbon gases.",
            citation = "OSHA 1910.157 Portable Fire Extinguishers",
            isA2L = true
        ),
        Question(
            id = "t1-95",
            section = EPASection.TYPE_I,
            questionText = "Under 40 CFR § 82.166, how long must wholesale refrigerant distributors retain sales records documenting the EPA certification numbers of individuals who purchase bulk refrigerants?",
            options = listOf("6 months", "1 year", "3 years", "Permanently"),
            correctIndex = 2,
            explanation = "Federal law mandates that wholesale distributors keep sales invoices showing technician names and certification numbers for at least three (3) years.",
            citation = "40 CFR § 82.166(a)",
            isA2L = false
        ),
        Question(
            id = "t1-96",
            section = EPASection.TYPE_I,
            questionText = "What is the typical holding charge in a new replacement domestic refrigerator evaporator coil shipped from the manufacturer?",
            options = listOf("A pressurized holding charge of dry nitrogen (typically 10 to 30 psig) to prove factory leak tightness", "A full operating charge of liquid R-134a", "Filled with compressor motor oil", "Shipped in a deep vacuum of 100 microns"),
            correctIndex = 0,
            explanation = "Evaporator coils are shipped with pressurized dry nitrogen. If no pressure releases when unsealing the shipping cap, the component has a factory leak and must not be installed.",
            citation = "OEM Replacement Parts Guide",
            isA2L = false
        ),
        Question(
            id = "t1-97",
            section = EPASection.TYPE_I,
            questionText = "Why must technicians avoid breathing vapors from recovered compressor oil drained from a burned-out system?",
            options = listOf("The vapors smell like sweet perfume", "Motor burnout decomposes oil and refrigerant into caustic acids (hydrofluoric, hydrochloric) and toxic fumes that cause chemical pulmonary burns", "The vapors will turn the technician's hair white", "The vapors contain radioactive isotopes"),
            correctIndex = 1,
            explanation = "Burnout oil contains dissolved halogen acids that emit severe respiratory irritants upon contact with air; inhalation can cause serious chemical lung damage.",
            citation = "NIOSH Chemical Hazard Guidelines",
            isA2L = false
        ),
        Question(
            id = "t1-98",
            section = EPASection.TYPE_I,
            questionText = "What is the proper method for neutralizing acidic compressor oil spills on shop floors or inside service vans?",
            options = listOf("Pour gasoline over the spill", "Neutralize with baking soda (sodium bicarbonate) or an industrial acid-neutralizing absorbent, then wash with water", "Cover with flammable dry wood sawdust", "Leave it to evaporate naturally"),
            correctIndex = 1,
            explanation = "Sodium bicarbonate safely neutralizes strong halogen acids, converting them into benign salts and water for safe disposal.",
            citation = "OSHA Laboratory Safety Guidance",
            isA2L = false
        ),
        Question(
            id = "t1-99",
            section = EPASection.TYPE_I,
            questionText = "What is the fundamental difference between Type I and Type II certifications under EPA Section 608?",
            options = listOf("Type I covers small hermetic appliances with 5 lbs or less of refrigerant; Type II covers high-pressure commercial and residential systems containing more than 5 lbs", "Type I covers automotive air conditioning; Type II covers home refrigerators", "Type I covers low-pressure chillers; Type II covers ammonia plants", "There is no difference; the two are identical"),
            correctIndex = 0,
            explanation = "Type I applies strictly to small factory-sealed appliances <= 5 lbs. Type II applies to high-pressure appliances (split systems, commercial refrigeration, rooftop units).",
            citation = "40 CFR § 82.161",
            isA2L = false
        ),
        Question(
            id = "t1-100",
            section = EPASection.TYPE_I,
            questionText = "What is the single most effective way to ensure an EPA Section 608 Type I small appliance repair will remain leak-free over its operating lifetime?",
            options = listOf("Install five piercing saddle valves on all tubes", "Braze permanent copper process stubs with high-silver alloy while nitrogen purging, verify with a standing 150 psig nitrogen hold, and deep evacuate to 500 microns", "Seal all joints with household silicone caulk", "Wrap all fittings with black electrical tape"),
            correctIndex = 1,
            explanation = "Permanent brazed joints, nitrogen purging to prevent internal oxide scale, standing pressure verification, and deep vacuum dehydration ensure leak-free, acid-free longevity.",
            citation = "EPA 608 Type I Excellence Standards",
            isA2L = false
        )
    )
}
