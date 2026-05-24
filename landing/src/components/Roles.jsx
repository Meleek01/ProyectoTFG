import { Fragment } from 'react'

const roles = [
  { icon: '🌱', name: 'Héroe Novato' },
  { icon: '⚔️', name: 'Guerrero' },
  { icon: '🛡️', name: 'Campeón' },
  { icon: '👑', name: 'Leyenda' },
  { icon: '⚡', name: 'Titán Legendario', highlight: true },
]

export default function Roles() {
  return (
    <section className="roles">
      <div className="container">
        <h2 className="section-title">Tu camino de héroe</h2>
        <p className="section-subtitle">A medida que subes de nivel, tu rango narrativo evoluciona</p>
        <div className="roles-track">
          {roles.map((role, i) => (
            <Fragment key={role.name}>
              <div className={`role-item${role.highlight ? ' highlight' : ''}`}>
                <span className="role-icon">{role.icon}</span>
                <span className="role-name">{role.name}</span>
              </div>
              {i < roles.length - 1 && <div className="role-arrow">→</div>}
            </Fragment>
          ))}
        </div>
      </div>
    </section>
  )
}
