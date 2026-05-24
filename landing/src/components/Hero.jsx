const DownloadIcon = () => (
  <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
    <path d="M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4" />
    <polyline points="7 10 12 15 17 10" />
    <line x1="12" y1="15" x2="12" y2="3" />
  </svg>
)

export default function Hero({ user, onLoginClick, onLogout }) {
  return (
    <header className="hero">
      <div className="hero-bg"></div>
      <nav className="nav">
        <span className="logo">⚔️ FitHero</span>
        <div className="nav-right">
          {user ? (
            <>
              <span className="nav-user">⚔️ {user}</span>
              <button className="nav-cta nav-cta-ghost" onClick={onLogout}>Cerrar sesión</button>
            </>
          ) : (
            <>
              <button className="nav-cta nav-cta-ghost" onClick={onLoginClick}>Iniciar sesión</button>
              <a className="nav-cta" href="#download">Descargar APK</a>
            </>
          )}
        </div>
      </nav>
      <div className="hero-content">
        <div className="badge">TFG · DAM 2026</div>
        <h1>Convierte tu <span className="accent">entrenamiento</span><br />en una épica aventura</h1>
        <p className="subtitle">
          FitHero es una app de fitness gamificada con mecánicas RPG. Completa misiones diarias,
          sube de nivel, gana monedas y desbloquea equipamiento para tu héroe.
        </p>
        <div className="hero-buttons">
          <a className="btn btn-primary" href="#download">
            <DownloadIcon />
            Descargar gratis
          </a>
          <a className="btn btn-secondary" href="#features">Ver características</a>
        </div>
      </div>
      <div className="scroll-hint">↓</div>
    </header>
  )
}
