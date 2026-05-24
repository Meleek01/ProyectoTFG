const techs = [
  { logo: '🟦', name: 'Kotlin 1.9' },
  { logo: '🎨', name: 'Jetpack Compose' },
  { logo: '📐', name: 'Material Design 3' },
  { logo: '🧭', name: 'Navigation Compose' },
  { logo: '🔄', name: 'ViewModel + StateFlow' },
  { logo: '🖼️', name: 'Coil 2.6' },
]

export default function TechStack() {
  return (
    <section className="tech">
      <div className="container">
        <h2 className="section-title">Stack técnico</h2>
        <div className="tech-grid">
          {techs.map((tech) => (
            <div key={tech.name} className="tech-item">
              <span className="tech-logo">{tech.logo}</span>
              <span>{tech.name}</span>
            </div>
          ))}
        </div>
      </div>
    </section>
  )
}
