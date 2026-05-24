const features = [
  {
    icon: '🗡️',
    title: 'Misiones diarias',
    desc: () => <>Completa retos de <strong>fuerza, cardio, salud, hábito y mente</strong>. Cada categoría tiene sus propias misiones con recompensas de XP y monedas.</>,
  },
  {
    icon: '⬆️',
    title: 'Sistema de niveles RPG',
    desc: () => <>Gana XP con cada misión completada. El umbral de nivel sube exponencialmente (factor 1.4x), igual que en un RPG real. Tu rango cambia de <em>Héroe Novato</em> a <em>Titán Legendario</em>.</>,
  },
  {
    icon: '💰',
    title: 'Economía de monedas',
    desc: () => <>Gana monedas al ratio 10:1 respecto al XP. Gástalas en la tienda para obtener potenciadores, equipamiento especial y objetos únicos.</>,
  },
  {
    icon: '🏆',
    title: 'Sistema de logros',
    desc: () => <>20 logros que se calculan en tiempo real según tu progreso. Desde el primero hasta los más épicos que requieren semanas de constancia.</>,
  },
  {
    icon: '🛒',
    title: 'Tienda in-app',
    desc: () => <>Tres pestañas: <strong>Potenciadores</strong> para obtener ventajas temporales, <strong>Equipamiento</strong> para personalizar a tu héroe, y objetos <strong>Especiales</strong> únicos.</>,
  },
  {
    icon: '📱',
    title: 'Android nativo',
    desc: () => <>Construida con <strong>Kotlin + Jetpack Compose</strong> y Material Design 3. Fluida, moderna y optimizada para Android 8.0+.</>,
  },
]

export default function Features() {
  return (
    <section className="features" id="features">
      <div className="container">
        <h2 className="section-title">¿Qué puedes hacer?</h2>
        <div className="features-grid">
          {features.map((f) => (
            <div key={f.title} className="feature-card">
              <span className="feature-icon">{f.icon}</span>
              <h3>{f.title}</h3>
              <p>{f.desc()}</p>
            </div>
          ))}
        </div>
      </div>
    </section>
  )
}
