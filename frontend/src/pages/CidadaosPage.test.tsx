import { render, screen, waitFor } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { MemoryRouter } from "react-router-dom";
import { beforeEach, describe, expect, it, vi } from "vitest";
import * as cidadaosApi from "../api/cidadaos";
import { CidadaosPage } from "./CidadaosPage";

vi.mock("../api/cidadaos");

function renderPage() {
  return render(
    <MemoryRouter>
      <CidadaosPage />
    </MemoryRouter>,
  );
}

describe("CidadaosPage", () => {
  beforeEach(() => {
    vi.resetAllMocks();
    vi.mocked(cidadaosApi.listarCidadaos).mockResolvedValue([
      { id: "c1", nome: "Ana", email: "ana@test.com", telefone: "419999" },
    ]);
  });

  it("exibe cidadãos da listagem", async () => {
    renderPage();

    expect(await screen.findByText("Ana")).toBeInTheDocument();
    expect(screen.getByText("ana@test.com")).toBeInTheDocument();
  });

  it("cadastra um novo cidadão e recarrega a lista", async () => {
    const user = userEvent.setup();
    vi.mocked(cidadaosApi.criarCidadao).mockResolvedValue({
      id: "c2",
      nome: "Bruno",
      email: "bruno@test.com",
      telefone: "",
    });

    renderPage();
    await screen.findByText("Ana");

    await user.type(screen.getByPlaceholderText("Maria Silva"), "Bruno");
    await user.type(screen.getByPlaceholderText("maria@email.com"), "bruno@test.com");
    await user.click(screen.getByRole("button", { name: /cadastrar/i }));

    await waitFor(() => {
      expect(cidadaosApi.criarCidadao).toHaveBeenCalledWith({
        nome: "Bruno",
        email: "bruno@test.com",
        telefone: "",
      });
    });
    expect(cidadaosApi.listarCidadaos).toHaveBeenCalledTimes(2);
  });
});
