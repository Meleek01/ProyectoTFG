const DownloadIcon = () => (
  <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
    <path d="M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4" />
    <polyline points="7 10 12 15 17 10" />
    <line x1="12" y1="15" x2="12" y2="3" />
  </svg>
)

export default function Download() {
  return (
    <section className="download" id="download">
      <div className="container">
        <div className="download-card">
          <div className="download-text">
            <h2>Descarga FitHero</h2>
            <p>Instala la app directamente en tu dispositivo Android. Requiere Android 8.0 (API 26) o superior.</p>
            <div className="requirements">
              <span className="req">📱 Android 8.0+</span>
              <span className="req">💾 ~15 MB</span>
              <span className="req">🆓 Completamente gratis</span>
            </div>
            <div className="install-note">
              <strong>Nota:</strong> Al instalar un APK externo, ve a <em>Ajustes → Seguridad → Instalar apps desconocidas</em> y actívalo para tu navegador o gestor de archivos.
            </div>
            <a
              className="btn btn-primary btn-large"
              href="https://github.com/Meleek01/ProyectoTFG/releases/latest/download/FitHero.apk"
              id="apk-download"
            >
              <DownloadIcon />
              Descargar APK
            </a>
          </div>
          <div className="download-visual">
            <div className="phone-mockup">
              <div className="phone-screen">
                <div className="screen-header">FitHero</div>
                <div className="screen-xp">
                  <span>Nivel 7 · Guerrero</span>
                  <div className="xp-bar">
                    <div className="xp-fill" style={{ width: '65%' }}></div>
                  </div>
                </div>
                <div className="screen-mission">
                  <div className="mission-item">⚔️ 50 flexiones <span className="m-xp">+120 XP</span></div>
                  <div className="mission-item">🏃 Correr 5km <span className="m-xp">+200 XP</span></div>
                  <div className="mission-item completed">💧 2L de agua ✓</div>
                </div>
                <div className="screen-coins">💰 1.340 monedas</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  )
}
