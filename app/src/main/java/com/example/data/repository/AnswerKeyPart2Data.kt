package com.example.data.repository

import com.example.data.model.Lesson
import com.example.data.model.LessonSections
import com.example.data.model.QuizQuestion
import com.example.data.model.TermItem
import com.example.data.model.TrendDiagnosticItem

fun getAnswerKeyModulePart2(): Lesson {
    return Lesson(
        id = "key_part_2",
        moduleId = "mod_key",
        number = "K.2",
        title = "I—IV Бүлэг (1—36) Хариултын сан",
        summary = "SI нэгж, атом-хими, масс баланс, редокс, pH, AN шинж, концентраци, тодорхойгүй байдал, термодинамик, дулаан-масс дамжуулалт, эмульс, түүхий эд, QC ба traceability",
        estimatedMinutes = 30,
        sections = LessonSections(
            зорилго = "1-ээс 36 хүртэлх химийн инженерчлэл, термодинамик, эмульсийн шинжлэх ухаан ба үйлдвэрлэлийн чанарын хяналтын асуултуудын стандарт хариултуудыг тулгах.",
            нэр_томьёо = listOf(
                TermItem("Хариултын түлхүүр 1-36", "Химийн инженерчлэл, эмульсийн матриц, чанарын хяналт ба термодинамикийн 1-ээс 36 дугаар сэдвүүдийн нэгдсэн хариулт.", "mod_key", "K.2")
            ),
            онол = """
НЭГДСЭН ХАРИУЛТЫН САН (СЭДЭВ 1 — 36):

[1 · SI нэгж, хэмжээс ба тоон сахилга]:
1. Нягтын хэмжээс: [M L⁻³].
2. 2.5 kg/min -> kg/h: 150 kg/h.
3. °C харьцаанд хэрэглэхгүй шалтгаан: °C-ийн тэг цэг абсолют бус; ratio-д K (Кельвин) шаардлагатай.
4. Significant figures: Хэмжилтийн мэдээллийн үндэслэлтэй нарийвчлал.
5. Canonical unit давуу тал: Нэг физик хэмжээг unit сонголтоос үл хамааран хадгална.
6. Аравтын таслал ба цэг: Таслал ба цэгийг ижил decimal separator болгон validate хийнэ.

[2 · Атом, ион ба химийн холбоо]:
1. Ион: Цэнэгтэй атом/бүлэг.
2. CaCl₂ charge balance: +2 ба 2×−1 тэнцэнэ.
3. Concentration vs activity: Concentration нь нэг эзэлхүүн дэх amount; activity нь effective thermodynamic quantity.
4. Ус ионыг сольватжуулах шалтгаан: Туйл ба ион--диполийн харилцан үйлчлэл.
5. Identity proof-ийн 4 өгөгдөл: Нэр, CAS, formula, grade/lot.
6. Conductivity ба температур: Mobility ба conductivity температурын функц.

[3 · Моль, стехиометр ба масс баланс]:
1. n=m/M нэгж: kg/(kg/mol)=mol.
2. Mass fraction vs mole fraction: Mass basis vs amount basis.
3. Steady state: Сонгосон хугацаанд accumulation тэг.
4. Баланс хаагдахгүй үеийн 4 шалгалт: Boundary, time, stream completeness, measurement system.
5. 45 kg residual: Inventory ба uncertainty дутуу.
6. Unknown их үеийн calculator төлөв: Underdetermined state, missing variables, хамаарах хэсгийг disabled.

[4 · Исэлдэлтийн зэрэг ба редокс баланс]:
1. Исэлдүүлэгч: Электрон авна, өөрөө ангижирна.
2. NO₃⁻ дахь N-ийн исэлдэлтийн зэрэг: +5.
3. Half-reaction method: Атом, цэнэг, электроныг тэнцүүлдэг.
4. Oxygen balance хязгаар: Convention/products/conditions/context-оос хамаарна.
5. Хий ялгарах сэжигт: ERP дагуу тусгаарлах, мэдэгдэх, эрхгүй бол ойртохгүй.
6. Compatibility нотолгоо: SDS/TDS, matrix, authorized test, MOC.

[5 · Хүчил--суурь, pH ба буферийн ойлголт]:
1. pH тодорхойлолт: −log₁₀ aH+.
2. pH 1 нэгжийн өөрчлөлт: Идэвхжил (Activity) 10 дахин өөрчлөгдөнө.
3. Buffer capacity vs pH: Нэг нь өөрчлөлт эсэргүүцэх чадвар, нөгөө нь төлөвийн тоо.
4. Температурын нөлөө: Nernst slope, equilibrium, sample state өөрчлөгдөнө.
5. Calibration record: Buffers/lot, slope, offset, temp, electrode/meter ID, time.
6. Out-of-calibration статус: Invalid/hold; investigation ба approved remeasurement.

[6 · Аммонийн нитратын бүтэц, шинж ба хяналтын хил]:
1. AN ионууд: NH₄⁺, NO₃⁻.
2. Oxidizer vs fuel: Oxidizer electron acceptance/combustion support; fuel oxidation-д энерги өгнө.
3. Hygroscopicity нөлөө: Moisture, caking, flow, composition basis өөрчилнө.
4. 0.30% moisture fraction: 0.0030.
5. Receiving record: Identity, lot, CoA, condition, sampling/method, storage transfer.
6. Contamination сэжигт: Identity ба hazard state үл мэдэгдэх тул эрх бүхий control шаардлагатай.

[7 · Концентрацийн суурь ба холилтын тооцоо]:
1. Mass fraction denominator: Нийт масс.
2. Component balance: m1*w1 + m2*w2 = (m1 + m2)*wf.
3. pH weighted average хийж болохгүй шалтгаан: Log activity ба chemistry nonlinear.
4. Mass<->volume: Density + temperature/basis шаардлагатай.
5. Unknown remainder шошго: Missing/unknown component; таахгүй.
6. Partial calculator: Known component masses, missing information, final disabled state.

[8 · Өгөгдлийн чанар, тодорхойгүй байдал ба инженерийн дүгнэлт]:
1. Accuracy vs precision: Reference-д ойр vs давталтын ойролцоо.
2. Bias: Систематик зөрүүний estimate.
3. Range uncertainty budget мөн үү: Үгүй, зөвхөн тархалтын энгийн descriptor.
4. Conformity statement: Result, U, rule, spec/method revision, scope.
5. Retest үед анхны дүн: Хадгалагдаж deviation/retest-тэй холбогдоно.
6. Data lineage 5 талбар: Sample, analyst, time, method, instrument/calibration/raw file.

[9 · Термодинамикийн I хууль ба энергийн баланс]:
1. Heat vs temperature: Heat нь transfer; temperature нь state.
2. kW хэмжигдэхүүн: Power буюу J/s.
3. Q=m*cp*ΔT assumptions: Single phase, constant cp, no loss/work.
4. Open-system energy stream: Enthalpy зэрэг energy.
5. Actual duty их гарахад: Boundary, mass, ΔT, time, losses, meter/sensor.
6. Input дутуу үед: Symbolic formula, missing data, calculation unavailable.

[10 · Термодинамикийн II хууль, тэнцвэр ба driving force]:
1. ΔG заалт: Тодорхой нөхцөл дэх thermodynamic direction.
2. Equilibrium vs steady state: Equilibrium net driving force тэг; steady state flows байж болно.
3. T яагаад Kelvin: Thermodynamic absolute scale.
4. Metastable: Local stable, global equilibrium биш.
5. Nucleation barrier: Favorable change-ийн rate-ийг саатуулна.
6. Shelf-life нотолгоо: Validated time/temperature stability studies, method, lots, acceptance.

[11 · Дулаан дамжуулалт: conduction, convection, exchanger]:
1. Conduction vs convection: Molecular gradient vs fluid/boundary motion.
2. Fouling нөлөө: Resistance өсгөж U-г бууруулна.
3. Q_dot = U*A*ΔT нэгж: W (Ватт).
4. Bulk vs surface temperature: Орон зайн өөр measurands.
5. Heat-up drift-д: Mass, temperatures, flows, pressure, cleaning, sensor status.
6. Cold spot: Local phase/viscosity/crystallization өөрчлөлт үүсч болно.

[12 · Масс дамжуулалт, диффуз ба интерфейс]:
1. Flux нэгж: mol/(m²·s) зэрэг.
2. Fick-ийн сөрөг тэмдэг: Ихээс бага концентраци чиглэл.
3. Mixing diffusion-ийг орлох уу: Үгүй; bulk transport-ыг нэмэгдүүлнэ.
4. k property биш шалтгаан: Hydrodynamics/geometry/properties нөлөөлнө.
5. Scale-up A/V: Геометрээс хамаарч өөрчлөгдөнө.
6. Холилтын жигд байдлын нотолгоо: Validated sampling plan, time/position data, method uncertainty.

[13 · Шингэний урсгал, даралт ба насосны систем]:
1. Mass vs volume flow: m_dot = rho * Q.
2. Reynolds number: Inertia / viscous forces.
3. Cavitation нөхцөл: Local absolute pressure нь vapor pressure-аас буурах.
4. Operating point: Pump curve ба system curve огтлолцол.
5. Flow drop trends: P suction/discharge, Q, T, current, valves, ΔP filter.
6. Absolute pressure: Vapor pressure absolute basis-тэй тул.

[14 · Холилт, residence time ба scale-up]:
1. Macro vs micromixing: Bulk homogenization vs smallest-scale mixing.
2. tau = V/Q: Идеал дундаж оршин суух хугацаа (Mean residence time).
3. Mean tau: Элемент бүрд адилгүй, хугацааны тархалт (RTD) байна.
4. Ижил RPM-д: Tip speed, P/V, Re, shear өөрчлөгдөнө.
5. RTD long tail: Dead zone/dispersion/interaction.
6. Scale-up change MOC мөн: Quality, heat, load, safety interactions өөрчлөгдөнө.

[15 · Дисперс систем ба коллоидын хэмжээсийн шатлал]:
1. Dispersed vs continuous phase: Тархсан хэсэг vs холбогдсон орчин.
2. A/V ба диаметр: Урвуу хамааралтай.
3. Brownian motion: Thermal molecular collisions-ийн санамсаргүй хөдөлгөөн.
4. Stokes model assumptions: Dilute, sphere, Newtonian, steady, known Δρ.
5. Mean size хязгаар: Coarse tail/polydispersity/multimodality-г нууна.
6. Microscopy metadata: Sample/prep, calibration, fields, weighting, time/point.

[16 · Интерфейсийн хүчдэл, норголт ба капиллярын даралт]:
1. Surface vs interfacial tension: Хийн интерфейс vs хоёр фазын хоорондох интерфейс.
2. Laplace pressure радиус багасахад: Өснө (ΔP = 2gamma/R).
3. Contact angle нөлөө: Chemistry, roughness, contamination, hysteresis, temp.
4. Dynamic tension: Адсорбцын хугацааны масштаб (adsorption timescale).
5. Нэгжийн шалгалт: N/m / m = N/m² = Pa.
6. Материалын өөрчлөлт: Wetting/compatibility/process behavior өөрчлөгдөнө.

[17 · Гадаргуугийн идэвхт бодис, adsorption ба HLB]:
1. Surfactant бүтэц: Hydrophilic (туйлт толгой) ба Lipophilic (туйлгүй сүүл).
2. HLB: Эмпирик гидрофил-липофил балансын индекс.
3. Tension vs stability: Интерфейсийн энерги vs нимгэн хальс/бүтэц/реологи/хөгшрөлт.
4. CMC нөлөөлөх хүчин зүйл: Найрлага, температур, ионы хүч, арга.
5. Blend HLB assumption: Шугаман эмпирик холилт.
6. Substitution нотолгоо: Identity, active content, compatibility, stability validation, MOC.

[18 · W/O ба O/W эмульс, phase inversion]:
1. W/O continuous phase: Тос (Oil).
2. Phase inversion: Фазуудын үүрэг солигдох (W/O -> O/W).
3. Conductivity: Усан фазын тасралтгүй холбогдсон байдлын proxy.
4. Volume fraction ганцаараа хангалтгүй: Affinity, температур, холилтын түүх нөлөөлнө.
5. Хослуулах evidence: Цахилгаан дамжуулалт, шингэрүүлэлтийн сорил, микроскоп/будагч.
6. Ambiguous үед: Hold/missing info/retest, тохируулга хийхгүй.

[19 · Дуслын хэмжээ, тархалт ба хэмжилтийн арга]:
1. D50: Кумулятив 50%-ийн голч.
2. Span томьёо: (D90 - D10) / D50.
3. Volume weighting: Эзлэхүүн голчийн кубээр өсдөг тул (V ∝ d³) том дусалд маш мэдрэмтгий.
4. Microscopy prep bias: Хэв гажилт, дуслын нийлэлт, талбайн сонголтын хазайлт.
5. D50 тогтвортой D90 өсөхөд: Coarse tail (том дуслын сүүл) өссөн таамаглал.
6. Дуслын тайлангийн metadata: Арга, жигнэлтийн суурь, дээж бэлтгэл, калибровк, зургууд, сегментаци, U.

[20 · Реологи: Newtonian бус урсгал ба thixotropy]:
1. Shear stress / rate units: Pa; s⁻¹.
2. n < 1: Shear-thinning (псевдопластик).
3. Thixotropy: Хугацаанаас хамаарсан эвдрэл ба эргэн сэргэлт.
4. Yield stress model-dependent: Ашигласан загвар, мужаас хамаарч өөр гарна.
5. Lab/process холбоо: Температур, насжилт, муруй, урсгал Q, ΔP, мотор гүйдэл.
6. Single point хязгаар: Муруй, гистерезис ба yield төлөвийг нууна.

[21 · Эмульсийн тогтворгүйжилт ба эвдрэлийн механизм]:
1. Flocculation vs coalescence: Хальс бүрэн бөөгнөрөл vs хальс тасарч нэгдэх.
2. Creaming: Дуслын бие даасан байдал (identity) хадгалагдана.
3. Ostwald ripening: Continuous phase-аар молекулын диффузээр масс шилжинэ.
4. D90 өсөлт: Coarse tail / coalescence / ripening hypothesis.
5. Accelerated test validate хийх шалтгаан: Хурдасгасан механизмын хурд бодит хугацаатай ижил эсэх тодорхойгүй.
6. Release шийдвэр: Батлагдсан протокол, зөвшөөрөгдөх хязгаар, төлөөлөх үр дүн, эрх бүхий албан тушаалтан.

[22 · AN prill-ийн бүтэц, сүвэрхэг чанар ба grade]:
1. Bulk vs particle density: Мөхлөг хоорондын хөндийг тооцсон vs зөвхөн мөхлөгийн өөрийн нягт.
2. Porosity нөлөө: Тос шингээлт, бат бэх, урсамтгай чанар, барьцалдалт.
3. Loose density: m / V_bulk.
4. PSD sampling: Ялгаралт (segregation), нарийн ширхэгийн төлөөлөл.
5. Grade substitution: Материал, процесс, аюулын зан төлөв өөр байж болно.
6. CoA-аас гадна receiving evidence: Identity, лац/савлагаа, тээвэр/хадгалалт, дээж/арга, партийн түүх.

[23 · Чийг, caking ба хадгалалтын орчны нөлөө]:
1. Wet-basis denominator: Нийт нойтон масс.
2. Water activity vs moisture: Нийт усны агууламж vs усны химийн потенциал, боломжит чөлөөт байдал.
3. Caking bridge mechanism: Шингэн/капиллярын гүүр -> Хатуу/талстын гүүр рүү шилжих.
4. RH ба T хамаарал: Ханасан уурын даралт температураас хамаарна.
5. LOD (Loss on Drying) дутагдал: Уснаас бусад дэгдэмхий бодисын массын хорогдлыг ялгахгүй.
6. Warehouse investigation: T/RH зураглал, хаалга/цаг агаар, савлагаа/паллет, хадгалсан насжилт, дээж, арга.

[24 · Нүүрсустөрөгчийн түлшний ерөнхий шинж ба чанар]:
1. Flash point: Өөрөө асах температур биш, аюулгүй ажиллагааны дээд хязгаар биш.
2. Кинематик зуурамтгай чанар: ν = μ / ρ [m²/s].
3. Density reference temperature: Дулааны тэлэлтээс шалтгаалж нягт өөрчлөгддөг тул.
4. Түлш хүлээн авах бүртгэл: Identity, CoA, лац, савны цэвэрлэгээ, дээж, танкны түүх.
5. Төлөөлөх усны дээж: Баталгаажсан дээд/дунд/доод эсвэл ёроолын усны дээжлэлтийн төлөвлөгөө.
6. Тодорхойгүй орлуулалт: Quarantine / MOC / Approval.

[25 · Эмульгатор, тос, лав ба туслах бодисын материалын хяналт]:
1. Active content: Бүтээгдэхүүн дэх цэвэр идэвхт бодисын массын хувь.
2. Carrier үүрэг: Уусгагч, тээвэрлэгч ба боловсруулалтын орчин.
3. Лавын шилжилт: Олон компонентын талсжилтын тархалт тул нэг цэгээр илэрхийлэгдэхгүй.
4. Орлуулалтын нотолгоо: Identity, идэвхт агууламж, нийцэмж, тогтворжилт, процессын баталгаажуулалт, MOC.
5. Фаз салсан материал: Hold / Quarantine.
6. Active-basis conversion assumption: Идэвхт бодисын агууламжийн шинжилгээ үнэн, молекулын бүтэц эквивалент.

[26 · Мэдрэмжжүүлэгч ба нэмэлт бодисын ангилал, identity ба хяналт]:
1. Химийн vs физикийн мэдрэмжжүүлэлт: In-situ хий үүсгэх урвал vs Урьдчилан бэлтгэсэн микросфер/хөндийт бичил бүтэц.
2. Bulk density хязгаар: Хөндийн хэмжээний тархалт ба динамик даралтын хариу үйлдлийг илэрхийлэхгүй.
3. Идэвхт масс: m_active = m_product * x_active.
4. Remainder-ийг таахгүй шалтгаан: Тээвэрлэгч тос, уусгагч болон хольцын найрлага үл мэдэгдэх тул.
5. Хүлээн авалтын QC: Агууламж/тогтворжилт vs Бөөмийн хэмжээ/нягт/бат бэх.
6. Identity алдаа: Stop / Hold / Quarantine / Escalate.

[27 · Усны чанар, ион бохирдол ба материалын нийцэмж]:
1. Conductivity юу хэмжихгүй вэ: Ионуудын химийн нэр төрөл, найрлага.
2. Hardness basis: Эквивалент конвенци (жишээ нь mg/L CaCO₃) өөр өөр байж болно.
3. Хатуу хэсэг (Particles): Нуклеацын төв үүсгэх, хоолой бөглөх, насос элэгдүүлэх, бохирдуулах.
4. Усны эх үүсвэр солигдох: Химийн найрлага, процесс ба материалын эрсдэл өөрчлөгдөх тул MOC шаардана.
5. Зэврэлтийн хувьсагчид: Cl⁻, pH, ууссан хүчилтөрөгч, температур, металл, урсгалын хурд.
6. Усны шинжилгээний тайлан: Эх үүсвэр/цэг/цаг, T, хадгалалт, арга, калибровк, үр дүн ба U.

[28 · Нийлүүлэгчийн чанар, CoA ба өөрчлөлтийн удирдлага]:
1. CoA vs Specification: CoA бол тухайн партийн бодит үр дүнгийн гэрчилгээ; Spec бол хүлээн авах шаардлага.
2. Paired data: Нийлүүлэгч ба худалдан авагчийн лабораторийн хоорондын аргын ба системийн хазайлтыг үнэлнэ.
3. Нийлүүлэгчийн мэдэгдэл (Change notification): Эрсдэлийн үнэлгээ / MOC / Дахин баталгаажуулалтыг эхлүүлнэ.
4. Нийлүүлэгчийн үнэлгээний карт: Чанар, нийлүүлэлт, алдааны давтамж, өөрчлөлтийн мэдэгдэл, CAPA.
5. Skip-lot testing: Түүхэн өгөгдлийн найдвартай нотолгоо, эрсдэлийн үнэлгээнд үндэслэнэ.
6. Spec-д нийцсэн ч drift байвал: Тренд, далд шинж, өөрчлөлтийг мөрдөн шалгаж, disposition дүрмийг мөрдөнө.

[29 · Исэлдүүлэгч уусмал: найрлага, уусалт ба масс баланс]:
1. Уусах чанар vs хурд: Уусах чанар нь термодинамик тэнцвэрийн хэмжээ; хурд нь тэнцвэрт хүрэх кинетик хугацаа.
2. Түүхий эдийн чийг: Σ(m_i * x_moisture)-ээр орсон нийт усыг тооцож, нэмэх усны хэрэгцээнээс хасна.
3. Нягт ба температур: Эзлэхүүн ба нягт нь температурын хүчтэй функц тул.
4. Нэмэх ус сөрөг гарах үед: Түүхий эдийн чийг, цэвэршилт, жин, зорилтот концентрацийн суурь, хэмжилтийн алдаа дутуу.
5. Lot traceability баримтууд: CoA, batch sheet, жигнүүрийн лог, дээж/үр дүн, шилжүүлгийн акт.
6. Масс зөрүүний 3 шалгалт: Нэгж ба tare; калибровк/статус; материалын шилжүүлэг/reconciliation.

[30 · Талстжилт ба дулааны нөөц]:
1. Хэт ханалт (Supersaturation): Тэнцвэрийн хэмжээнээс их бодис ууссан метастабиль төлөв.
2. Нуклеацад нөлөөлөх хүчин зүйлс: Найрлага, савны гадаргуу, механик хольц, хөргөлтийн түүх, хугацаа.
3. Effective margin бүрэлдэхүүн: T_cold, T_limit, U (тодорхойгүй байдал), процессын вариац.
4. Cold spot аюул: Орон нутгийн талстжилт хамгийн хүйтэн цэгээс эхэлж систем бүхэлдээ түгжрэх эрсдэлтэй.
5. Нийлүүлэгч солигдох: Түүхий эдийн цэвэршилт, чийг, хольцын ионы найрлага өөрчлөгдөх тул MOC шаардана.
6. Margin эерэг гарсан ч шалгах зүйлс: Acceptance criterion, method validity, sensor status, хариуцагчийн зөвшөөрөл.

[31 · Түлшний фаз: суурь, нийцтэй байдал ба тунгийн илэрхийлэл]:
1. Нийт ба фазын суурийн ялгаа: e_t = m_e / M_total; e_p = m_e / m_fuel = e_t / f.
2. Суурь дутуу хувь: Denominator тодорхойгүй тул физик утгагүй.
3. Compatibility нотлох 3 баримт: SDS/TDS, нийлүүлэгчийн баталгаа, нийцэмжийн баталгаажсан сорил.
4. e_t = 1%, f = 10% үед: e_p = 1% / 0.10 = 10% (зөвхөн ижил mass basis үед).
5. Материал орлуулалт: MOC, эрсдэлийн үнэлгээ, баталгаажуулалт, батлагдсан зөвшөөрөл.
6. Фаз салалтын эхний өгөгдөл: Парти, температур, холилтын горим, микроскоп ба өмнөх тохирсон багцтай харьцуулах.

[32 · Эмульсжүүлэлт: интерфейс, дуслын тархалт ба процессын түүх]:
1. W/O эмульсийн continuous phase: Тосон фаз (Oil phase).
2. Коалесценц (Coalescence): Дуслуудын хоорондох нимгэн хальс тасарч нэг том дусал болон нийлэх.
3. Laplace pressure ба радиус: Радиус багасахад урвуу хамаарлаар өснө (ΔP = 2γ/R).
4. RPM яагаад shear-ийн шууд хэмжүүр биш вэ: Ротор-статорын геометр, урсгал, зуурамтгай чанар, завсрын зай, хугацаа зэрэг олон хүчин зүйл нөлөөлдөг.
5. Дуслын тархалтын тайлан: Арга, дээжлэлтийн протокол, төлөөлөх дуслын тархалтын хувиуд (D10, D50, D90, Span).
6. Тренд шилжихэд шалгах 3 бүлэг: Материал; Тоног төхөөрөмж/процесс; Хэмжилтийн тогтолцоо.

[33 · Матрицын чанарын хяналт ба хэмжилтийн баталгаа]:
1. Measurand: Хэмжихээр нарийн тодорхойлсон физик хэмжигдэхүүн.
2. Specification vs Control limit: Spec нь хэрэглэгчийн/бүтээгдэхүүний шаардлага; Control limit нь үйлдвэрлэлийн процессын статистик зан төлөв.
3. Tare-тэй нягтын томьёо: ρ = (m_filled - m_empty) / V_cal.
4. Зуурамтгай чанар ба температур: Зуурамтгай чанар нь температураас экспоненциал хамаардаг тул.
5. Outlier гарвал баримтжуулах: Анхны түүхий өгөгдөл, мөрдөн шалгалт, давтан сорил, deviation акт, эрх бүхий шийдвэр.
6. Conformity тогтооход дутуу зүйлс: Тодорхойгүй байдал U, шийдвэрийн дүрэм (decision rule), багажийн калибровк, дээжийн төлөөлөх чадвар, аргын хувилбар.

[34 · Мэдрэмжжүүлэлт: бүтэц, хугацаа ба нягтын тайлбар]:
1. Proxy: Шууд хэмжих боломжгүй хэмжигдэхүүнийг төлөөлөх шууд бус параметр.
2. ρ_bulk ≈ ρ_matrix*(1 - φ) таамаглал: Хийн массыг тэг гэж үзсэн, эзлэхүүн идеал нэмэгдсэн, нөхцөл ижил.
3. Хугацааны тэг цэг (Time zero): Химийн хийжилт эхэлсэн хугацааг нэг суурьт оруулж муруйг харьцуулах.
4. Гүний нөхцөл vs гадаргуу: Цооногийн ёроолын гидростатик даралт, температур, оршин суух хугацаа эрс өөр.
5. Input дутуу үед тооцоолуур: Missing information харуулж, хамаарах үр дүнгүүдийг идэвхгүй болгоно.
6. Нягт хэвийн ч өөр QC зөрвөл: Hold хийж, бүх нотолгоог нягтална; нягтыг бүхнийг шийдэх ганц үзүүлэлт болгож ашиглахгүй.

[35 · Буцаалт, дахин боловсруулалт ба хаягдлын засаглал]:
1. Return vs Rework: Return нь талбайгаас буцаж ирсэн гарал үүсэл; Rework нь батлагдсан зааврын дагуу дахин боловсруулах албан ёсны статус.
2. Weighted average хүчинтэй нөхцөл: Чанар аддитив шинжтэй, нэг mass basis, хоорондоо нийцтэй популяциуд байх.
3. Reconciliation тэнцэл: Opening + Received = Used + Returned + Disposed + Closing + Discrepancy.
4. Identity тодорхойгүй үед: Quarantine / Hold.
5. Discrepancy эхний шалгалтууд: Хугацааны cutoff, нэгжийн алдаа, давхардсан/дутуу шилжүүлэг, жигнүүрийн калибровк, балансын хил.
6. Disposal instruction номд байхгүй шалтгаан: Талбайн тусгай хууль, ERP, SOP ба эрх бүхий зөвшөөрөл шаарддаг өндөр эрсдэлтэй үйл ажиллагаа тул.

[36 · Материалын статус, ангилал ба traceability]:
1. Status vs Classification: Status нь ажиллагааны төлөв (Hold, Quarantined, Released); Classification нь аюулын/хууль зүйн ангилал (Class 1.1D, 5.1).
2. Release-д шаардлагатай: Бүх сорилтын бүрэн дүн, баримтын хяналт, deviation хаагдсан байдал, эрх бүхий хүний баталгаажуулалт.
3. Lot Genealogy: Бүтээгдэхүүний эргүүлэн таталт (recall), чанарын мөрдөн шалгалт ба масс балансыг нотлоход.
4. Mismatch үед: Hold / Reconcile.
5. Residual-ийг алдагдал гэхгүй шалтгаан: Савны үлдэгдлийн тооллого ба хэмжилтийн тодорхойгүй байдал тайлагнаагүй тул.
6. Шошгын талбарууд: Материалын код, Lot дугаар, Одоогийн статус, Аюулын тэмдэг, Огноо, Хариуцагч ба байршил.
            """.trimIndent(),
            механизм = "Инженерийн бүх шалгалтын асуултууд нь суурь масс, дулаан, масс шилжилт, коллоид хими ба материалын чанарын зарчмуудаар бүрэн үндэслэгдэнэ.",
            хувьсагч_нэгж = "SI нэгжүүд, томьёоны хэмжээсүүд, статистикийн хувиуд (D10, D50, D90, Span, R²).",
            гарган_авалт = "1-ээс 36-р хичээлийн нэгдсэн хариултын сангаас шууд нэгтгэн гаргасан албан ёсны инженерийн түлхүүр.",
            жишээ = "19-2: Span = (D90 - D10) / D50; 29-2: Чийгийн массыг нийт уснаас хасах баланс.",
            нотолгоо = "ISO 9001, GHS, ASTM стандартууд.",
            тренд_оношлол = listOf(
                TrendDiagnosticItem(
                    signal = "Тооцооны суурь хуваарийг (Basis/Denominator) андуурах",
                    evidence = "B/A харьцаа ба хольцын нийт хувийг хооронд нь ижил гэж үзэх хандлага",
                    wrongConclusion = "Хувийн тооцоо бүх нөхцөлд ижил утга заана гэж дүгнэх",
                    correctAction = "Орцын харьцаа (B/A) ба нийт массын фракц (B/(A+B))-ийн хуваарийг тодорхой ялгах"
                )
            ),
            алдаа = "Хувийн суурийг (base-basis ба total-basis) андуурч тооцоолох.",
            дүгнэлт = "Тооцоо, сорилт бүр өөрийн хүчинтэй хязгаар ба хэмжилтийн тодорхойгүй байдалтай байна.",
            шалгах_асуултууд = listOf(
                QuizQuestion(
                    id = "q_key_2_1",
                    question = "Дуслын хэмжээний тархалтын Span-ийн зөв томьёо аль нь вэ?",
                    options = listOf(
                        "(D90 - D10) / D50",
                        "(D90 + D10) / 2",
                        "D90 / D10",
                        "(D50 - D10) / D90"
                    ),
                    correctAnswer = "(D90 - D10) / D50",
                    explanation = "Span нь 19-р сэдвийн дагуу (D90 - D10) / D50 томьёогоор тооцогдоно."
                )
            ),
            эх_сурвалж = "IV Бүлэг · Нэгдсэн хариултын сан 1—36."
        )
    )
}
