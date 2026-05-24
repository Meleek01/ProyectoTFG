export default function StatsBar() {
  return (
    <section className="stats-bar">
      <div className="stat">
        <span className="stat-number">5</span>
        <span className="stat-label">Categorías de misiones</span>
      </div>
      <div className="stat-divider"></div>
      <div className="stat">
        <span className="stat-number">20</span>
        <span className="stat-label">Logros desbloqueables</span>
      </div>
      <div className="stat-divider"></div>
      <div className="stat">
        <span className="stat-number">∞</span>
        <span className="stat-label">Niveles de héroe</span>
      </div>
      <div className="stat-divider"></div>
      <div className="stat">
        <span className="stat-number">3</span>
        <span className="stat-label">Tiendas de equipamiento</span>
      </div>
    </section>
  )
}
